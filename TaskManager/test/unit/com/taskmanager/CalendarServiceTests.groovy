package com.taskmanager

import grails.test.mixin.support.GrailsUnitTestMixin



/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CalendarService)
class CalendarServiceTests {

    void testGetWeekAndYear() {
		def calendarS = new CalendarService()
		def params = [:]
		def resultMap = calendarS.getWeekAndYear(params)
		Calendar cal = Calendar.getInstance()
		assert cal.get(Calendar.WEEK_OF_YEAR) == resultMap.weekNumber 
		assert cal.get(Calendar.YEAR) == resultMap.year
		def weekNumber = 1
		def year = 2000
		def direction = "next"
		params = [weekNumber:weekNumber,year:year,direction:direction]
		resultMap = calendarS.getWeekAndYear(params)
		assert 2  == resultMap.weekNumber
		assert 2000 == resultMap.year 
		direction = "previous"
		params.direction = direction
		resultMap = calendarS.getWeekAndYear(params)
		resultMap = calendarS.getWeekAndYear(params)
		assert 52  == resultMap.weekNumber //ostatni tydzien roku
		assert 1999 == resultMap.year
    }
	
	void testGetWeekDaysList() {
		def calendarS = new CalendarService()
		Calendar cal = Calendar.getInstance()
		def weekDays = calendarS.getWeekDaysList(cal)
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
		assertTrue calendarS.sameDay(cal.getTime(), weekDays[0])
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
		assertTrue calendarS.sameDay(cal.getTime(), weekDays[6])
	}
}
