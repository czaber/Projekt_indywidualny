package com.taskmanager

import static org.junit.Assert.*
import org.junit.*

class TaskIntegrationTests extends GroovyTestCase{
	def user
	def task1
	def task2
	def userTask1
	def userTask2
	
	def springSecurityService
	def calendarService
	def raportService
	def taskService
	
    @Before
    void setUp() {
		task1 = new Task(name: "test1" ).save()
		task2  = new Task(name: "test2" ).save()
		user = new User(
			username: 'test',
			password: 'test',
			enabled: true,
			firstName: 'test',
			lastName: 'test',
			email: 'test@gmail.com').save()
		
		userTask1 = new UserTask(user: user, task: task1 ).save()
		userTask2 = new UserTask(user: user, task: task2 ).save()
		
    }


    @Test
    void testAddPositionsToTaskAndConfirm() {
        def developerPanelC = new DeveloperPanelController()
		developerPanelC.springSecurityService.reauthenticate(user.username,user.password)

		def workHours = 8
		Calendar cal = Calendar.getInstance()
		def year = cal.get(Calendar.YEAR)
		def weekOfYear = cal.get(Calendar.WEEK_OF_YEAR)
		def weekday  = 0
		assert taskService.getHoursFromTask(task1,user,calendarService.getWeekDaysList(cal).getAt(weekday)) == [0,0,0,0]
		def model = developerPanelC.addRaport()
		assert model.taskPositionsMap.size() == 2 
		assert model.taskPositionsMap.get(task1) == null
		assert model.taskPositionsMap.get(task2) == null
		assert Raport.count == 0
		developerPanelC.params.taskId = task1.id
		developerPanelC.params.weekday = weekday
		developerPanelC.params.year = year
		developerPanelC.params.weekNumber = weekOfYear
		developerPanelC.params.changedValue = workHours
		developerPanelC.addPositionAjax()
		assert Raport.count == 1
		def raport = Raport.get(1)
		raport.dateConfirm = new Date() 
		def adminPanelC = new AdminPanelController()
		adminPanelC.confirmAllRaports()
		assert taskService.getHoursFromTask(task1,user,calendarService.getWeekDaysList(cal).getAt(weekday)) == [8,8,8,8]
		
    }
	
	@Test
	void testAddTaskToUserAndDelete() {
		def task3 = new Task(name: "test3").save(flush:true)
		def userId = user.id
		def adminPanelC = new AdminPanelController()
		def developerPanelC = new DeveloperPanelController()
		developerPanelC.springSecurityService.reauthenticate(user.username,user.password)
		def model = developerPanelC.tasksList()
		assert model.tasksUser.size() == 2
		adminPanelC.params.idDeveloper = user.id
		adminPanelC.params.idTask = task3.id
		adminPanelC.addTaskUser()
		model = developerPanelC.tasksList()
		assert  model.tasksUser.size() == 3
		adminPanelC.params.userId = user.id
		adminPanelC.params.taskId = task3.id
		adminPanelC.endTaskForUser()
		model = developerPanelC.tasksList()	
		assert model.tasksUser.size() == 2
		
	}
	
}
