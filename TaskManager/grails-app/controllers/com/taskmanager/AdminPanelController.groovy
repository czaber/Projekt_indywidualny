package com.taskmanager

import java.text.DateFormat
import java.text.SimpleDateFormat



class AdminPanelController {
	def taskService
	def raportService
	def sendMailService


	def index() {
		def c = Raport.createCriteria()
		def raports = c{
			isNotNull("dateConfirm")
			isNull("dateConfirmByAdmin")
			order("dateConfirm", "desc")
		}
		[raports:raports]
	}
	def activeUsers() {
		def users=User.findAllWhere(enabled:false)
		[users:users]
	}
	def active() {
		def user=User.get(params.id)
		def role = Role.get(params.role.asType(Long))
		UserRole.create(user, role, false)
		user.enabled=true
		sendMailService.sendMail(user.email,"Konto na TaskManger",'Twoje konto na TaskManger zostało aktywowane. Możesz się już logować do systemu.')
		flash.message = "Użytkownik ${user} został aktywowany i otrzymał prawa ${role}"
		redirect(action:'index')
	}
	
	
	def removeUser(){
		def user=User.get(params.id)
		sendMailService.sendMail(user.email,"Konto na TaskManger",'Admin odrzucił twoja prosbe o rejestracje.')
		user.delete()
		flash.message = "Użytkownik ${user} został skasowany."
		redirect(action:'index')
	}
	
	def createTask(){
		if(params.create && !params.name){
			def message = "Prosze podac nazwe zadania"
			return [message:message]
		}
		else if(params.name){
			def task = new Task(params)
			if(task.validate()){
				task.save()
				flash.message = "Zadanie ${task.name} została pomyślnie dodane do bazy danych."
				redirect(action:'createTask')
			}
			else {
				return [task:task]
			}
		}
	}


	def showEndedTasks(){
		def taskList  = Task.findAll { dateEnd != null }
		taskList.sort(true){a, b -> b.dateEnd <=> a.dateEnd }
		if(!taskList){
			flash.message = "Brak zakończonych zadań."
			redirect(action:'tasks')
		}
		[taskList:taskList]
	}
	def tasks(){
		def taskUsersMap = [:]
		def taskList  = Task.findAll { dateEnd == null }
		if(!taskList){
			flash.message = "Brak aktywnych zadań."
			redirect(action:'index')
		}
		
		for(task in taskList){
			def taskId = task.id
			def usersTasks = UserTask.findAll { task.id == taskId &&  confirm == false}
			if(usersTasks)
				taskUsersMap.put(task, usersTasks*.user)
			else
				taskUsersMap.put(task, null)
		}
			
		[taskUsersMap:taskUsersMap]
	}

	def showTask(){
		if(params.id){
			def  task = Task.get(params.id)
			def taskId = task.id
			def usersTasks = UserTask.findAll { task.id == taskId }

			usersTasks.sort(true){ it.user }

			def raportsUser = [:]

			for(userTask in usersTasks){
				raportsUser.put(userTask, taskService.getHoursFromTask(userTask.task, userTask.user,new Date()))
			}

			[raportsUser:raportsUser, task:task,id:params.id]
		}
		else
			redirect(action:'tasksList')

	}

	def show(){
		if(params.id){
			def  task = Task.get(params.id)
			def taskId = task.id
			def usersTasks = UserTask.findAll { task.id == taskId  }
	
			usersTasks.sort(true){ it.user }
	
	
			def today
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if(params.data){
				today = df.parse(params.data);
			}
			else
			today = new Date()
	
	
			def raportsUser = [:]
	
			for(userTask in usersTasks){
				raportsUser.put(userTask, taskService.getHoursFromTask(userTask.task, userTask.user,today))
			}
	
			render(template:"hoursPanel", model:[raportsUser: raportsUser])
		}

	}


	def showDetails(){
		if(params.userId && params.taskId){
			def id= params.taskId.asType(long)
			def (raportsList,raportsCount) = raportService.getUserTaskListAndCount(params)
			def task=Task.findWhere(id:id)
			def user=User.findWhere(id:params.userId.asType(long))
			return [raports:raportsList,raportSize:raportsCount,userId:params.userId,taskId:params.taskId, task:task.name, user:user.username]

		}
	}

	def endTasks(){
		def tasksNotEnded  = Task.findAll { dateEnd == null }
		def usersTasks
		def taskMap = [:]
		for(task in tasksNotEnded){
			def taskId = task.id
			usersTasks = UserTask.findAll {
				confirm == false && task.id == taskId
			}
			taskMap.put(task, usersTasks)
		}

		return [taskMap:taskMap]
	}

	def endTask(){
		if(params.id){
			def task = Task.get(params.id)
			def taskId = task.id
			def usersTasks  = UserTask.findAll{
				task.id == taskId && confirm == false
			}
			for(userTask in  usersTasks){
				if(!userTask.date)
				userTask.date = new Date()
				userTask.confirm = true
				sendMailService.sendMail(userTask.user.email,"Zadanie zakończone na TaskManger","Twoje zadanie ${userTask.task.name} zostało zakończone przez admina")
			}
			task.dateEnd = new Date()
			flash.message = "${task.name} zostało zakonczone dla wszytkich użytkowników."
			redirect(action:'endTasks')
		}

	}
	def endTaskForUser(){
		def userTask
		if(params.taskId && params.userId){
			def task=Task.get(params.taskId)
			def user=User.get(params.userId)
			userTask = UserTask.findWhere(task:task, user:user)
			if(!userTask.date)
			userTask.date = new Date()

			userTask.confirm = true


			sendMailService.sendMail(userTask.user.email,"Zadanie zakończone na TaskManger","Twoje zadanie ${userTask.task.name} zostało zakończone przez admina")

			flash.message = "${userTask.task.name} zostało zakonczone dla ${userTask.user.username}"
			redirect(action:'endTasks')
		}
		else
		redirect(action:'endTasks')
	}


	def addTaskToUserList(){  //WERSJA DO ZMIANY  dodanie javascript
		def tasksNotEnded  = Task.findAll { dateEnd == null }
		def users=User.findAllWhere(enabled:true)
		def usersTasks
		def usersForTask
		def usersNotForTask
		def taskMap = [:]
		for(task in tasksNotEnded){
			def taskId = task.id
			usersTasks = UserTask.findAll { task.id == taskId }
			usersForTask = usersTasks*.user
			usersNotForTask = users.findAll{
				if(!usersForTask.contains(it))
				return it
			}

			taskMap.put(task, usersNotForTask)
		}

		return [taskMap:taskMap]
	}
	def addTaskToUser(){                    //WERSJA DO ZMIANY
		if(params.id && params.user){
			def user = User.get(params.user.asType(Integer))
			def task  = Task.get(params.id)
			def userTask =  new UserTask(user: user, task: task)
			if(userTask.validate()){
				userTask.save()
				sendMailService.sendMail(user.email,"Nowe zadanie na TaskManger","Do twojego konta zostało dodane nowe zadanie - ${task.name}.")
				flash.message = "${task.name} zostało poprawnie dodane do zadan ${user.username}"
			}
		}
		redirect(action:'addTaskToUserList')
	}
	
	
	def notConfirmRaports(){
		def c = Raport.createCriteria()
		def raports = c{
			isNotNull("dateConfirm")
			isNull("dateConfirmByAdmin")
			order("dateConfirm","desc")
		}
		def raportHoursMap = [:]
		def hours
		for(raport in raports){
			hours = 0
			raport.positions.each {
				hours+= it.workHours
			}
			raportHoursMap.put(raport, hours)
		}
			
		[raportHoursMap:raportHoursMap]
	}

	def confirmRaport(){
		if(params.id){
			def raport = Raport.get(params.id)
			raport.dateConfirmByAdmin = new Date()
			sendMailService.sendMail(raport.creator.email,"Raport potwierdzony","Raport do zadania - ${raport.task.name} został potwierdzony przez admina.")
			flash.message = "Raport do ${raport.task.name} został zatwierdzony"
		}
		redirect(action: 'notConfirmRaports')
	}
	
	def cancelRaport(){
		if(params.id){
			def raport = Raport.get(params.id)
			raport.delete()	
			sendMailService.sendMail(raport.creator.email,"Raport anulowany","Raport do zadania - ${raport.task.name} został anulowany przez admina.")
			flash.message = "Raport do ${raport.task.name} został anulowany"
		}
		redirect(action: 'notConfirmRaports')
	}
	
	def confirmAllRaports(){
		def c = Raport.createCriteria()
		def raports = c{
			isNotNull("dateConfirm")
			isNull("dateConfirmByAdmin")
		}
		raports.each {
			it.dateConfirmByAdmin = new Date()
		}

		flash.message = "Wszystkie raporty zostały zatwierdzone"
		redirect(action: 'notConfirmRaports')
	}

}

