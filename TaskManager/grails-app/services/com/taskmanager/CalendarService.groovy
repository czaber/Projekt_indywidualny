package com.taskmanager

class CalendarService {

	/**
	 * Metoda zwracająca numer tygodnia i rok, które zostają zmienione w zależności od parametrów.
	 * params musie zawierać poprzedni numer tygodnia i rok a także 'direction' które oznacza w
	 * którą stronę został kalendarz przesunięty ("previous" i "next")
	 * @param params parametry wejsciowe
	 * @param String string
	 * @return [:weekNumber,:year]
	 */
    def getWeekAndYear(params) {
		def cal = Calendar.getInstance()
		def weekNumber = params.weekNumber.asType(Integer) //numer tygodnia w roku
		def year = params.year.asType(Integer)
		if(!year)
			year = cal.get(Calendar.YEAR)
			
		if(!weekNumber)
			weekNumber = cal.get(Calendar.WEEK_OF_YEAR)
				
				
		if(params.direction == "previous"){
			if(weekNumber == 1){ 						//1 tydzien roku
				year--
				weekNumber = 52
			}
			else
				weekNumber--
		}
		else if(params.direction == "next"){
			if(weekNumber == 52){
				weekNumber = 1
				year++
			}
			else
				weekNumber++
		}
		return [weekNumber:weekNumber,year:year]
		
		
    }
	
	
	/** Metoda zwracajaca tablicę z datami dla tygodnia aktualnego w kalendarzu, jednak posortowną dla domyslnej lokalizacji
	 * np pl pon-0,wt-1 itd
	 * 
	 * @param cal
	 * @return
	 */
	def getWeekDaysList(Calendar cal) {
		def wdays = []
		for(i in 1..7){
			cal.set(Calendar.DAY_OF_WEEK, i)
			wdays.add(cal.getTime())
		}
		
		return wdays.sort()
	}
	
	
	def getMonthDays(data){
		def cal = Calendar.getInstance()
		cal.setTime(data)
		def max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		def days = []
		for(int i = 1; i <= max; i++){
			cal.set(Calendar.DAY_OF_MONTH, i)
			days.add(cal.getTime())
		}
		
		return days
			
	}
	/**
	 * Metoda sprawdzajaca czy dwie daty są z tego samego dnia
	 * @param date1
	 * @param date2
	 * @return 
	 */
	boolean sameDay(date1, date2){
		Calendar cal1 = Calendar.getInstance()
		Calendar cal2 = Calendar.getInstance()
		cal1.setTime(date1)
		cal2.setTime(date2)
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
						  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
		
		return sameDay
						  				  
	}
	
}
