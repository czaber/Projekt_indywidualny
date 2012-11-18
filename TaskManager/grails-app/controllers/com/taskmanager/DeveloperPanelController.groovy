package com.taskmanager

import java.sql.SQLException
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import javax.validation.ValidationException
import org.apache.commons.lang.time.DateUtils


class DeveloperPanelController {

	def springSecurityService
	def calendarService
	def raportService
	def taskService
	def sendMailService
	def grailsApplication



	def index() {
	}
	def tasksList(){
		def user = User.get(springSecurityService.getCurrentUser().id)
		def userId  = user.id
		def tasksUser  = UserTask.findAll {
			user.id == userId && confirm == false
		}

		if(!tasksUser){
			flash.message = "Nie masz żadnych zadań aktywnych w tej chwili"
			redirect(action:'index')
		}

		return [tasksUser:tasksUser]
	}

	def showHistory(){
		def user = User.get(springSecurityService.getCurrentUser().id)
		def userId  = user.id
		def tasksUser  = UserTask.findAll {
			user.id == userId && confirm == true
		}
		tasksUser.sort(true){a, b -> b.date <=> a.date }
		if(!tasksUser){
			flash.message = "Nie masz żadnych zadań zakończonych"
			redirect(action:'tasksList')
		}

		return [tasksUser:tasksUser]
	}

	def endTask(){
		if(params.id){
			def taskUser  = UserTask.get(params.id)
			taskUser.date = new Date()
			flash.message = "${taskUser.task.name} zostało oznaczone jako wykonane, czeka na zatwiedzenie prze administatora."
			redirect(action:'tasksList')
		}
		else
			redirect(action:'tasksList')

	}

	def addRaport(){
		def user = User.get(springSecurityService.getCurrentUser().id)
		def userId  = user.id
		def tasksUser  = UserTask.findAll {
			user.id == userId && date == null
		}

		def tasks = tasksUser*.task
		if(!tasks){
			flash.message = "Nie masz żadnych zadań aktywnych w tej chwili"
			redirect(action:'index')
		}
		def cal = Calendar.getInstance()
		def map = calendarService.getWeekAndYear(params)
		def weekNumber = map.weekNumber
		def year = map.year

		cal.set(Calendar.YEAR,year)
		cal.set(Calendar.WEEK_OF_YEAR,weekNumber)

		def wdays = calendarService.getWeekDaysList(cal)

		def taskPositionsMap = taskService.createTasksPositionsList(tasks,userId, weekNumber, year)

		[taskPositionsMap:taskPositionsMap,weekdays:wdays,weekNumber:weekNumber,year:year]
	}


	def showDetails(){
		if(params.id){
			def user = User.get(springSecurityService.getCurrentUser().id)
			def task = Task.get(params.id)

			def taskRaport = taskService.getHoursFromTask(task, user,new Date())
			
			def tasksUser  = UserTask.findByTaskAndUser(task,user)
			[taskRaport:taskRaport,task:task,userId:user.id,id:params.id,tasksUser:tasksUser]
		}
		else
			redirect(action:'tasksList')
	}

	def showRaports(){
		if(params.userId && params.taskId){
			def id= params.taskId.asType(long)
			def (raportsList,raportsCount) = raportService.getUserTaskListAndCount(params)
			def task=Task.findWhere(id:id)
			return [raports:raportsList,raportSize:raportsCount,userId:params.userId,taskId:params.taskId,task:task.name]
		}
	}


	def show(){
		def user = User.get(springSecurityService.getCurrentUser().id)
		def task = Task.get(params.id)
		def today
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(params.data){
			today = df.parse(params.data);
		}
		else
			today = new Date()

		def taskRaport = taskService.getHoursFromTask(task, user,today)

		render(template:"hoursPanel", model:[taskRaport: taskRaport,task:task,userId:user.id])
	}


	def confirmRaport(){
		def user = User.get(springSecurityService.getCurrentUser().id)
		def weekNumber = params.weekNumber.asType(Integer)
		def year = params.year.asType(Integer)

		def raportNotConfirm = Raport.findAll{ dateConfirm == null }
		if(raportNotConfirm){
			raportNotConfirm.each {
				it.dateConfirm = new Date()
			}
	
			def mailBody = raportService.createBodyMailToConfirmRaport(raportNotConfirm, user)
			def adminMail = grailsApplication.config.adminMail
			sendMailService.sendMail(adminMail,"Nowe raporty",mailBody)
	
			flash.message = "Raporty zostały przesłane do admina,oczekują na zatwierdzenie"
			redirect(action:'index')
			return
		}
		else{
			flash.message = "Brak raportów do zatwierdzenia"
			redirect(action:'addRaport', params:[weekNumber:weekNumber,year:year])
		}
	}



	def addPositionAjax(){
		try {
			def user = User.get(springSecurityService.getCurrentUser().id)
			def userId  = user.id
			def task = Task.get(params.taskId.asType(Long))
			def taskId = task.id
			def weekday = params.weekday.asType(Integer)
			def weekNumber = params.weekNumber.asType(Integer)
			def year = params.year.asType(Integer)
			def hours = params.changedValue.asType(Integer)
			if(hours < 0 || hours > 24)
				throw new NumberFormatException()
			def raport = Raport.find{
				creator.id == userId && task.id == taskId && weekOfYear == weekNumber && year == year
			}
			if(!raport)
				raport = raportService.createRaportAndAddPosition(task, user, weekNumber, year)
			raportService.changePositionInRaport(raport, weekday, hours,null)
			render"Pozycja została poprawnie dodana"
		} catch (NumberFormatException e) {
			render"Proszę wpisywać poprawne nieujemne liczby całkowite"
		}
		catch (ValidationException e) {
			render"Wystąpił bład podczas zapisu"
		}
		catch (NullPointerException e) { //TESTOWE
			render"Wystąpił bład podczas zapisu(inject services nie zaddziałał)"
		}
	}
	
}
