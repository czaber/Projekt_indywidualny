package com.taskmanager


import javax.validation.ValidationException

import org.junit.Before;

import grails.test.mixin.support.GrailsUnitTestMixin


/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RaportService)
@Mock([Task,Raport,Position,User])
class RaportServiceTests{
	def raportS
	def task
	def user
	
	@Before
	void setUp() {
		raportS = new RaportService()
		raportS.calendarService = new CalendarService()
		task = new Task(name: "zadanie").save()
		user = new User(
			username: 'test',
			password: 'test',
			enabled: true,
			firstName: 'test',
			lastName: 'test',
			email: 'test@gmail.com').save()
	}
	
    void testCreateRaportAndAddPosition() {
		def weekOfYear = 1
		def year = ""
		def raport
		
		shouldFail(ValidationException) {
			raport = raportS.createRaportAndAddPosition(task, user, weekOfYear, year)
		}
		year  = 2000
		raport = raportS.createRaportAndAddPosition(task, user, weekOfYear, year)
		assert weekOfYear == raport.weekOfYear
		assert year == raport.year
		assert 7 == raport.positions.size()
		assert 1 == user.raports.size()
		assert 1 == task.raports.size()
	   
    }
	
	void testChangePositionInRaport() {
		def weekOfYear = 1
		def year = 2000
		def weekday = 5
		def raport = raportS.createRaportAndAddPosition(task, user, weekOfYear, year)

		
		def hours = 0
		raport.positions*.workHours.each {
			hours +=it
		}
		assert 0 == hours
		
		def dayHours = 5
		def description = ""
		
		weekday = -10 //weekday >=0 && <=6
		shouldFail(ValidationException) {
			raportS.changePositionInRaport(raport, weekday, dayHours, description)
		}
		weekday = 5
		raportS.changePositionInRaport(raport, weekday, dayHours, description)
		raport.positions*.workHours.each {
			hours +=it
		}
		def calendarService  = new CalendarService()
		assert dayHours == hours
		
		Calendar cal = Calendar.getInstance()
		cal.set(Calendar.YEAR,year)
		cal.set(Calendar.WEEK_OF_YEAR, weekOfYear)
		def weekdays = calendarService.getWeekDaysList(cal)
		
		def position = raport.positions.find {
			calendarService.sameDay(it.date, weekdays[weekday])
		}
		
		assert dayHours == position.workHours 
	}
	
	void testGetUserTaskListAndCount() {
		def weekOfYear = 1
		def year = 2000
		def weekday = 5
		def raport1 = raportS.createRaportAndAddPosition(task, user, weekOfYear, year)
		def raport2 = raportS.createRaportAndAddPosition(task, user, weekOfYear+1, year)
		def params = [:]
		def (raportsList,raportsCount) = raportS.getUserTaskListAndCount(params)
		assert raportsList == null
		assert raportsCount == null
		params = [userId:user.id,taskId:task.id]
		(raportsList,raportsCount) = raportS.getUserTaskListAndCount(params)
		assert 0 == raportsList.size()
		raport2.dateConfirmByAdmin = new Date()	//metoda bierze pod  uwagę tylko te raporty, które są zatwierdzone przez admina
		(raportsList,raportsCount) = raportS.getUserTaskListAndCount(params)
		assert 1 == raportsList.size()
		
	}
	
	
	
	
	
	
}
