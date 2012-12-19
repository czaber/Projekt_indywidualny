package com.taskmanager


import grails.test.mixin.support.GrailsUnitTestMixin



@TestFor(Task)
@Mock([User,Raport])
class TaskTests {

    void testCreating() {
      def task = new Task(name: "zadanie1")
	  mockForConstraintsTests(Task ,[ task ])
	  
	  def testTask = new Task()
	  assertFalse testTask.validate()

	  assertEquals "nullable",
				   testTask.errors.getFieldError("name").code
	  
	  testTask = new Task(name: "zadanie2")
	  assertTrue testTask.validate()
	  
    }
	
	void testAddingRaports(){
		def task = new Task(name: "zadanie").save()
		def user = new User(
				username: 'test',
				password: 'test',
				enabled: true,
				firstName: 'test',
				lastName: 'test',
				email: 'test@gmail.com').save()
				
		
		int n = 5
		
		for(int i = 0; i < n; i++) {
			def raport = new Raport(task: task, creator: user, weekOfYear: 1,year: 2000)
			task.addToRaports(raport)
		}
	
		assertEquals n, task.raports.size()
	}
	
	
}
