package com.taskmanager




/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Position)
class PositionTests {
	void testCreating() {
		def pos = new Position()
		assertFalse pos.validate()
  
		assertEquals "nullable",
					 pos.errors.getFieldError("workHours").code
		assertEquals "nullable",  pos.errors.getFieldError("date").code
		
		pos = new Position(date: new Date(),workHours: 5)
		assertTrue pos.validate()
		
	  }
}
