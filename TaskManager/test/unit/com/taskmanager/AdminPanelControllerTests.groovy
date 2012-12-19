package com.taskmanager

import com.sun.mail.iap.Response;

import grails.plugins.springsecurity.SpringSecurityService

import grails.test.mixin.support.GrailsUnitTestMixin



/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(AdminPanelController)
@Mock([User,Task,UserTask,Raport])
class AdminPanelControllerTests {
	def user
	def task1
	def task2
	def userTask1
	def userTask2
	def raport
	
	@Before
	void setUp() {
		defineBeans {
			raportService(RaportService)
			taskService(TaskService)
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
		userTask1 = new UserTask(user: user, task: task1 ).save()
		userTask2 = new UserTask(user: user, task: task2 ).save()
		raport = new Raport(task: task1, creator: user, weekOfYear: 1,year: 2000,dateConfirm:new Date()).save()
		task1.addToRaports(raport)
	}
	
	void testCreateTask() {
		def name = "test3"
		params.name = name
		params.create = ""
		controller.createTask()
		def task = Task.findByName(name)
		assert  name == task.name
		
	}
    void testTasksList() {
		task1.dateEnd = null
		task2.dateEnd = null
      	def model = controller.tasksList()
		assert 2 == model.taskList.size()
		assert 1 == model.tasksUserMap.size()
		assert  [task1,task2] == model.tasksUserMap.getAt(user).sort()
    }
	void testShowEndedTasks() {
		task1.dateEnd = new Date()
		task2.dateEnd = new Date()
		task1.save()
		task2.save()
		def model = controller.showEndedTasks()
		assert 2 == model.taskList.size()

	}
	void testShowEndedTasksEmpty() {
		task1.dateEnd = null
		task2.dateEnd = null
		def model = controller.showEndedTasks()
		assert '/adminPanel/tasks' == response.redirectedUrl 

	}

	void testShowTask() {
		params.id = task1.id
		def model = controller.showTask()
		assert 1 == model.raportsUser.size()
		assert [0,0,0,0] == model.raportsUser.getAt(userTask1)
	}
	
	void testShowTaskWithoutId() {
		def model = controller.showTask()
		assert '/adminPanel/tasksList' == response.redirectedUrl
	}
	void testShow() {
		params.id = task1.id
		views['/adminPanel/_hoursPanel.gsp'] = 'cos'
		controller.show()
		assert 'cos' == response.text
	}
	
	void testShowDetails() {
		params.taskId = task1.id
		params.userId = user.id
		def model = controller.showDetails()
		assert 0 == model.raportSize
	}
	
	void testEndTasks() {
		userTask1.confirm = true
		userTask1.save()
		def model = controller.endTasks()
		assert 2 == model.taskMap.size()
		assert 0 == model.taskMap.get(task1).size()
		assert 1 == model.taskMap.get(task2).size()
		assert userTask2.id == model.taskMap.get(task2)[0].id
	}
	void testEndTask() {
		controller.sendMailService = mockMailService()
		
		def task3 = new Task(name: "test3" ).save()
		params.id = task3.id
		def userTask3 = new UserTask(user: user, task: task3 ).save()
		controller.endTask()
		assert task3.dateEnd != null
		assert userTask3.confirm == true
		assert userTask3.date != null
	}
	
	void testEndTaskForUser() {
		controller.sendMailService = mockMailService()

		def task3 = new Task(name: "test3" ).save()
		params.taskId = task3.id
		params.userId = user.id
		def userTask3 = new UserTask(user: user, task: task3 ).save()
		controller.endTaskForUser()
		assert userTask3.confirm == true
		assert userTask3.date != null
	}
	
	void testAddTaskUser() {
		controller.sendMailService = mockMailService()

		def task3 = new Task(name: "test3" ).save()
		params.idTask = task3.id
		params.idDeveloper = user.id
		controller.addTaskUser()
		def userTask = UserTask.findByTaskAndUser(task3,user)
		assert  task3.name == userTask.task.name
	}
	
	void testAddTaskUserExists() {
		controller.sendMailService = mockMailService()
		params.idTask = task2.id
		params.idDeveloper = user.id
		userTask2.confirm = true
		controller.addTaskUser()
		assert  userTask2.confirm == false //ustawia confirm ponownie na false
	}
	
	
	void testNotConfirmRaports() {
		def model = controller.notConfirmRaports()
		assert 1 == model.raportHoursMap.size()
	}
	
	void testConfirmRaport(){
		controller.sendMailService = mockMailService()
		params.id = raport.id
		assert raport.dateConfirmByAdmin == null
		controller.confirmRaport()
		assert raport.dateConfirmByAdmin != null
		assert '/adminPanel/notConfirmRaports' == response.redirectedUrl
	}
	
	void testConfirmAllRaports(){
		controller.confirmAllRaports()
		assert raport.dateConfirmByAdmin != null
		assert '/adminPanel/notConfirmRaports' == response.redirectedUrl
	}
	
	def mockMailService(){
		def mailS = new SendMailService()
		mailS.metaClass.sendMail = { a, b, c->}
		return mailS
	}
}
