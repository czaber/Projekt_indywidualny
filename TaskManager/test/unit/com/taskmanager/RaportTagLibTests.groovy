package com.taskmanager



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(RaportTagLib)
@Mock([Raport,Task,User])
class RaportTagLibTests {

    void testRaportActivity() {
		def task1 = new Task(name: "test1" ).save()
		def user = new User(
			username: 'test',
			password: 'test',
			enabled: true,
			firstName: 'test',
			lastName: 'test',
			email: 'test@gmail.com').save()
		def raport = new Raport(task: task1, creator: user, weekOfYear: 1,year: 2000,dateConfirm:new Date()).save()
		
		def format = "dd-MM-yyyy"
		def message = "Użytkownik ${raport.creator.username} stworzył raport do ${raport.task.name} w dniu "
		message+= new java.text.SimpleDateFormat(format).format(raport.dateConfirm)
		
		assert applyTemplate('<gMy:raportActivity format="${format}" raport="${raport}"/>', [format: format,raport:raport]) == message
    }
}
