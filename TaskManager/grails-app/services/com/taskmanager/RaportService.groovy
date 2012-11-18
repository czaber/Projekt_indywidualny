package com.taskmanager

import javax.validation.ValidationException;

class RaportService {
	def calendarService
	
	def int MAX_ENTRIES_PER_PAGE = 5
	/**
	 * Metoda zwracająca listę raportów dla danego usera i taska(params musi zawierac pola userId i taskId) z offsetem i maxSize
	 * dla pagination, a takżę poczatkowy rozmiar całej tablicy
	 * @param params
	 * @return [ raports, raportSize ]
	 */
    def getUserTaskListAndCount(params) {
		if(params.taskId && params.userId){
			def task = Task.get(params.taskId)
			def userId = params.userId.asType(Integer)
			def raports = task.raports.findAll{
				if(it.creator.id == userId && it.dateConfirmByAdmin!=null)
					return it
			}
			def raportSize = raports.size()
			def offset = params.offset.asType(Integer)
			if(!offset)
				offset = 0
			def max = params.max.asType(Integer)
			if(!max)
				max = MAX_ENTRIES_PER_PAGE
			raports = raports.toList()
			
			raports.sort(true){a,b->
				if(a.weekOfYear == b.weekOfYear)
					return b.year <=> a.year
				else
					return b.weekOfYear <=> a.weekOfYear
			}
			raports = raports.subList(offset, Math.min(offset+max, raports.size()))
			[ raports, raportSize ]
			
		}
		else 
			return [ null, null ]
	
	

    }
	
	/**
	 * Metoda tworząca nowy raport dla danego zadania i usera. Razem z raportem tworzona jest lista pozycji do niego
	 * z wartościami domyślnymi
	 * @param task
	 * @param user
	 * @param weekOfYear
	 * @param year
	 * @return stworzony raport
	 */
	def createRaportAndAddPosition(task,user,weekOfYear,year){
		Raport rap = new Raport(task:task,creator: user,weekOfYear:weekOfYear,year: year)
		if(!rap.validate())
			throw new ValidationException()
		def positions = []
		Calendar cal = Calendar.getInstance()
		cal.set(Calendar.YEAR,year)
		cal.set(Calendar.WEEK_OF_YEAR, weekOfYear)
		def weekdays = calendarService.getWeekDaysList(cal)
		def hours
		for(i in 0..6){
			def pos = new Position(date: weekdays[i],workHours: 0 )
			if(!pos.validate()){
				throw new ValidationException()
			}
			else{
				pos.save()
				positions.add(pos)
			}
		}
		
		rap.save()
		positions.each {
			rap.addToPositions(it)
		}
		user.addToRaports(rap)
		task.addToRaports(rap)
		return rap
	}
	
	/**
	 * Metoda zmieniajaca pozycję dla danego raportu
	 * @param raport
	 * @param weekday numer dnia w tygodniu
	 * @param positionHours liczba h do zmiany
	 * @param description	opis pozycji do zmiany
	 * @return
	 */
	def changePositionInRaport(raport,weekday,positionHours,description){
		if(raport == null || weekday < 0 || weekday > 6)
			throw new ValidationException("Bląd przy zmianie wlasności pozycji")
		Calendar cal = Calendar.getInstance()
		cal.set(Calendar.YEAR,raport.year)
		cal.set(Calendar.WEEK_OF_YEAR, raport.weekOfYear)
		def weekdays = calendarService.getWeekDaysList(cal)
		def day =  weekdays[weekday]
		def positions = raport.positions
		def pos = positions.find {
			if(calendarService.sameDay(it.date, day))
				return it
		}
		if(pos==null)
			throw new ValidationException("${day} nie należy do własności raportu - ${raport.weekOfYear} i ${raport.year}")
		if(positionHours!=null)
			pos.workHours = positionHours
		if(description!=null){
			pos.description = description
			if(!pos.validate())
				throw new ValidationException("Opis pozycji jest za długi.Proszę podawąć max 200 znaków.")
		}
	}
	
	/**
	 * Metoda tworząca treść maila informującego o zatwierdzeniu raportów dla danego usera
	 * @param raportConfirm 
	 * @param user
	 * @return treśc maila
	 */
	def createBodyMailToConfirmRaport(raportConfirm,user){
		def tasks = raportConfirm*.task
		tasks.unique(true)
		def mailBody = "${user.username} dodał nowe raporty do "
		tasks.each {
			mailBody += it.name +","
		}
		mailBody = mailBody.substring(0, mailBody.size()-1) +"."
	}
	
	
	
}
