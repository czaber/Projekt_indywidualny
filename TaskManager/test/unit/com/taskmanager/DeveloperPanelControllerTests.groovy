package com.taskmanager


import org.junit.Before;

import grails.plugins.springsecurity.SpringSecurityService

import grails.test.mixin.support.GrailsUnitTestMixin





@TestFor(DeveloperPanelController)
@Mock([User,Task,UserTask,Raport,Position])
class DeveloperPanelControllerTests {
	def user
	def task1
	def task2
	def userTask1
	def userTask2
	def raport
	
	@Before
	void setUp() {
		defineBeans {
			calendarService(CalendarService)
			taskService(TaskService)
			raportService(RaportService)
		}
		task1 = new Task(name: "test1" ).save()
		task2  = new Task(name: "test2" ).save()
		User.metaClass.encodePassword = { -> "encryptedPassword"}
		user = new User(
			username: 'test',
			password: 'test',
			enabled: true,
			firstName: 'test',
			lastName: 'test',
			email: 'test@gmail.com').save()
			
		def springS = new SpringSecurityService()
		springS.metaClass.getCurrentUser = {return user}
		controller.springSecurityService = springS
		
		userTask1 = new UserTask(user: user, task: task1 ).save()
		userTask2 = new UserTask(user: user, task: task2 ).save()
		raport = new Raport(task: task1, creator: user, weekOfYear: 1,year: 2000).save()
		task1.addToRaports(raport)
	}
	
    void testTasksList() {
		def model = controller.tasksList()
		assert model.tasksUser.size() == 2
    }
	
	void testShowHistory() {
		userTask1.confirm = true
		userTask1.save()
		def model = controller.showHistory()
		assert model.tasksUser.size() ==  1 //pokazuje tylko zadania potwiedzone przez admina
	}
	void testEndTask(){
		params.id = userTask1.id
		assert userTask1.date == null
		controller.endTask()
		assert userTask1.date != null
	}
	
	void testEndTaskWithoutId(){
		controller.endTask()
		assert '/developerPanel/tasksList' == response.redirectedUrl 
	}
	
	void testAddRaport(){
		def model = controller.addRaport()
		assert model.taskPositionsMap.size() == 2
		assert model.taskPositionsMap.get(task1) == null
		assert model.taskPositionsMap.get(task2) == null
	}
	
	void testShowDetails() {
		params.id = task1.id
		def model = controller.showDetails()
		assert [0,0,0,0] == model.taskRaport
	}
	void testShowDetailsWithoutId() {
		controller.showDetails()
		assert '/developerPanel/tasksList' == response.redirectedUrl 
	}
	
	void testShow() {
		params.id = task1.id
		views['/developerPanel/_hoursPanel.gsp'] = 'cos'
		controller.show()
		assert 'cos' == response.text
	}
	
	void testConfirmRaport(){
		controller.raportService.metaClass.createBodyMailToConfirmRaport = { a, b->}
		controller.sendMailService = mockMailService()
		params.weekNumber = 1
		params.year = 2000
		assert raport.dateConfirm == null
		controller.confirmRaport()
		assert raport.dateConfirm != null
		assert '/developerPanel/index' == response.redirectedUrl
	}
	
	void testConfirmRaportZeroRaports(){
		raport.dateConfirm = new Date()
		raport.save()
		controller.confirmRaport()
		assert '/developerPanel/addRaport?weekNumber=&year=' == response.redirectedUrl
	}
	
	void testAddPositionAjax(){
		params.taskId = task1.id
		params.weekday = 0
		params.year = 2000
		params.weekNumber = 1
		params.changedValue = 8
		controller.raportService.metaClass.createRaportAndAddPosition = { a, b,c,d->}
		controller.raportService.metaClass.changePositionInRaport = { a, b,c,d->}
		controller.addPositionAjax()
		assert "Pozycja została poprawnie dodana" == response.text
	}
	
	void testAddPositionAjaxNumberFormatEx(){
		params.taskId = task1.id
		params.weekday = 0
		params.year = 2000
		params.weekNumber = 1
		params.changedValue = "dfdfd"
		controller.addPositionAjax()
		assert "Proszę wpisywać poprawne nieujemne liczby całkowite" == response.text
	}
	
	def mockMailService(){
		def mailS = new SendMailService()
		mailS.metaClass.sendMail = { a, b, c->}
		return mailS
	}
}
