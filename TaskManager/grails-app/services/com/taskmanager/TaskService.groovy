package com.taskmanager

import java.awt.List;
import javax.validation.ValidationException

class TaskService {

	/**
	 * Metoda tworząca listę zawierająca liczbę h przepracowanych w dniu ,tygodniu, miesiącu i całościowo nad tym zadaniem przez danego pracownika
	 * @param task
	 * @param user Jeśli null metoda liczy liczbę h przepracowanych nad zadaniem przez cały zespół
	 * @param date dzień według którego ma liczyć godziny w tygoniu i miesiącu
	 * @return lista h w dniu,tygodniu, miesiącu i całościowo
	 */
    def getHoursFromTask(task,user,Date date) { 
		if(task == null || date == null)
			throw new ValidationException()
		def hoursList
		def hours
		def hoursInDay
		def hoursInWeek
		def hoursInMonth
		def positions
		def positionsInDay
		def positionsInWeek
		def positionsInMonth
		def calendar = Calendar.getInstance()
		def raports
		calendar.setTime(date)
		def day =  calendar.get(Calendar.DAY_OF_MONTH)
		def weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
		def year = calendar.get(Calendar.YEAR)
		def month = calendar.get(Calendar.MONTH)
	
	
		hoursList = []
		hours = 0
		hoursInDay = 0 
		hoursInWeek = 0
		hoursInMonth = 0
		
		if(user){
			def userId = user.id
			raports = task.raports.findAll{ 
				if(it.creator.id == userId  && it.dateConfirmByAdmin!=null)
					return it
			}
		}
		else{
			raports = task.raports.findAll{
				if(it.dateConfirmByAdmin!=null)
					return it
			}
		}

		positions = raports.positions
		
		positions = positions.flatten()
		
		positions.workHours*.each {
			hours +=it
		}
		
		
		positionsInWeek = positions.findAll{
			calendar.setTime(it.date)
			if(calendar.get(Calendar.WEEK_OF_YEAR) == weekOfYear && calendar.get(Calendar.YEAR) == year)
				return it
		}
		if(positionsInWeek){
			positionsInWeek.workHours*.each {
				hoursInWeek +=it
			}
		}

		
		positionsInMonth = positions.findAll{
			calendar.setTime(it.date)
			if(calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.YEAR) == year)
				return it
		}
		
		if(positionsInMonth){
			positionsInMonth.workHours*.each {
			hoursInMonth +=it
			}
		}
		
		
		
		
		positionsInDay = positions.findAll{
			calendar.setTime(it.date)
			if(calendar.get(Calendar.DAY_OF_MONTH) == day && calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.YEAR) == year)
				return it
		}
		if(positionsInDay){
			positionsInDay.workHours*.each {
			hoursInDay +=it
			}
		}
		
		hoursList.add(hoursInDay)
		hoursList.add(hoursInWeek)
		hoursList.add(hoursInMonth)
		hoursList.add(hours)
		return hoursList
    }

	
	/**
	 * Metoda tworząca mapę w której task jest kluczem natomiast wartością jest lista  pozycji (z jednego tygodnia).Wartość jest null(nie ma żadnych pozycji)
	 * gdy nie został stworzony jeszcze raport dla danego taska w danym tygodniu 
	 * @param tasks lista wszytkich zadań aktywnych dla usera
	 * @param userId
	 * @param weekNumber numer tygodnia w roku
	 * @param year
	 * @return mapa w którym kluczem jest task natomiast warością pozycje dla tego task w tygodniu weekNumber
	 */
	def createTasksPositionsList(tasks,userId,weekNumber,year){
		def c = Raport.createCriteria()
		def raports = c{
			and{
				creator { idEq(userId) }
				eq("weekOfYear", weekNumber)
				eq("year", year)
				isNotNull("dateConfirm")
			}
		}
		def tasksBad =  raports*.task


		def tasksGood = tasks.findAll {
			if(!tasksBad.contains(it))
				return it
		}
		tasksGood.sort()

		def taskPositionsMap = [:]
		def raport
		def positions
		def taskId
		for(task in tasksGood){
			taskId = task.id
			raport = Raport.find {
				creator.id == userId && task.id == taskId && weekOfYear == weekNumber && year == year
			}
			if(raport)
				positions = raport.positions.asList()
			else
				positions = null
			taskPositionsMap.put(task, positions)
		}
		return taskPositionsMap
	}
	/**
	 * Metoda zwracajaca statystyke miesieczną dla danego usera
	 * @param user
	 * @param date data z okreslonego miesiaca
	 * @return  [taskHoursMap,allHours] 
	 * <br>
	 * (taskHoursMap - mapa której kluczem jest task a wartością lista godzin w dniu, tyg,mies,ogolem przepracowanych nad taskiem
	 * 	<br>
	 * allHours - liczba wszytkich godzin przepracowanych w dniu, tyg,mies,ogolem dal zadan które było wykonwyane w tym miesiacu )
	 */
	def getUserMonthStatistic(user,date){
		def userId  = user.id
		def tasksUser  = UserTask.findAll {
			user.id == userId
		}
		
		def taskHoursMap = [:]
		def hoursList = []
		def allHours = [0,0,0,0]
		for(taskUser in tasksUser){
			hoursList = this.getHoursFromTask(taskUser.task, user, date)
			if(hoursList[2] != 0){ 					//zadania nad którymi robiono cokolwiek w tym miesiacu
				taskHoursMap.put(taskUser.task, hoursList)
				allHours[0] += hoursList[0]
				allHours[1] += hoursList[1]
				allHours[2] += hoursList[2]
				allHours[3] += hoursList[3]
			}
		}
		return [taskHoursMap,allHours]
	}
	
	
}
