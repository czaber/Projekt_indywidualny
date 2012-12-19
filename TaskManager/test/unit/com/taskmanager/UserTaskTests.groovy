package com.taskmanager



import grails.test.mixin.support.GrailsUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(UserTask)
@Mock([User,Task])
class UserTaskTests {

    void testCreating() {
      	def userTask = new UserTask()
		assertFalse userTask.validate()
		assertEquals "nullable",userTask.errors.getFieldError("user").code
		assertEquals "nullable",userTask.errors.getFieldError("task").code
		def task = new Task(name: "zadanie").save()
		def user = new User(
				username: 'test',
				password: 'test',
				enabled: true,
				firstName: 'test',
				lastName: 'test',
				email: 'test@gmail.com').save()
		userTask = new UserTask(user: user, task: task )
		assertTrue userTask.validate()
		assertFalse userTask.confirm //domyślnie false
    }
}
