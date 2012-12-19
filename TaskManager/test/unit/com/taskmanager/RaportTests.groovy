package com.taskmanager

import grails.test.mixin.support.GrailsUnitTestMixin
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Raport)
@Mock([User,Task,Position])
class RaportTests {
	
	void testCreating() {
		def raport = new Raport()
		assertFalse raport.validate()
		assertEquals "nullable",raport.errors.getFieldError("creator").code
		assertEquals "nullable",raport.errors.getFieldError("weekOfYear").code
		assertEquals "nullable",raport.errors.getFieldError("year").code
		def task = new Task(name: "zadanie").save()
		def user = new User(
				username: 'test',
				password: 'test',
				enabled: true,
				firstName: 'test',
				lastName: 'test',
				email: 'test@gmail.com').save()
		raport = new Raport(task: task, creator: user, weekOfYear: 1,year: 2000)
		assertTrue raport.validate()
	}

	void testAddingPositions(){
		def task = new Task(name: "zadanie").save()
		def user = new User(
				username: 'test',
				password: 'test',
				enabled: true,
				firstName: 'test',
				lastName: 'test',
				email: 'test@gmail.com').save()
		def raport = new Raport(task: task, creator: user, weekOfYear: 1,year: 2000)

		int n = 7
		def date = new Date()
		
		for(int i = 0; i < n; i++) {
			def pos = new Position(date: date,workHours: i).save()
			raport.addToPositions(pos)
		}
		assertEquals 1, raport.positions.size()		//sortedSet positions - compareTo porównujaćy date
		raport = new Raport(task: task, creator: user, weekOfYear: 1,year: 2000)
		for(int i = 0; i < n; i++) {
			date += 1
			def pos = new Position(date: date,workHours: i).save()
			raport.addToPositions(pos)
		}
		assertEquals n, raport.positions.size()
	}
}
