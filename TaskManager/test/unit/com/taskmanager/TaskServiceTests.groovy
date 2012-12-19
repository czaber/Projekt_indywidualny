package com.taskmanager



import org.junit.Before;

import grails.test.mixin.support.GrailsUnitTestMixin




@TestFor(TaskService)
@Mock([Task,Raport,Position,User])
class TaskServiceTests {
	def taskService
	def task
	def user
	def workHours
	def weekOfYear
	def year
	@Before
	void setUp() {
		taskService = new TaskService()
		task = new Task(name: "zadanie").save()
		User.metaClass.encodePassword = { -> "encryptedPassword"}
		user = new User(
			username: 'test',
			password: 'test',
			enabled: true,
			firstName: 'test',
			lastName: 'test',
			email: 'test@gmail.com').save()
		weekOfYear = 2
		year = 2012
		workHours = 8
		Calendar cal = Calendar.getInstance()
		cal.set(Calendar.YEAR,year)
		cal.set(Calendar.WEEK_OF_YEAR, weekOfYear)
		
		def raport1 = new Raport(task: task, creator: user, weekOfYear: weekOfYear,year: year,dateConfirmByAdmin:new Date()).save()
		task.addToRaports(raport1)
		
		cal.set(Calendar.DAY_OF_WEEK, 2)
		raport1.addToPositions(new Position(date: cal.getTime(),workHours: workHours))	//weekNumber 2 DAY_OF_WEEK 2
		cal.set(Calendar.DAY_OF_WEEK, 3)
		raport1.addToPositions(new Position(date: cal.getTime(),workHours: workHours)) //weekNumber 2 DAY_OF_WEEK 3
		
		weekOfYear = 3
		cal.set(Calendar.WEEK_OF_YEAR, weekOfYear)
		
		def raport2 = new Raport(task: task, creator: user, weekOfYear: weekOfYear,year: year,dateConfirmByAdmin:new Date()).save()
		task.addToRaports(raport2)
		
		cal.set(Calendar.DAY_OF_WEEK, 2)
		raport2.addToPositions(new Position(date: cal.getTime(),workHours: workHours)) //weekNumber 3 DAY_OF_WEEK 2
		cal.set(Calendar.DAY_OF_WEEK, 3)
		raport2.addToPositions(new Position(date: cal.getTime(),workHours: workHours)) //weekNumber 3 DAY_OF_WEEK 3
	}
	
	
    void testGetHoursFromTask() {	
		Calendar cal = Calendar.getInstance()
		cal.set(Calendar.YEAR,year)
		cal.set(Calendar.WEEK_OF_YEAR, weekOfYear)
		cal.set(Calendar.DAY_OF_WEEK, 2)
		def hoursList = taskService.getHoursFromTask(task, null, cal.getTime())
		assert workHours==hoursList[0]
		assert 2*workHours==hoursList[1]
		assert 4*workHours==hoursList[2]
		assert 4*workHours==hoursList[3]
    }
	
	
	void testCreateTasksPositionsList() {
		def taskPositionsMap = taskService.createTasksPositionsList([task],user.id,weekOfYear,year)
		assert taskPositionsMap.size() == 1
		assert taskPositionsMap.get(task).size() == 2
		
	}
	
}
