package com.taskmanager

class RegisterController {
	def springSecurityService
	def sendMailService
	def index(UserRegistrationCommand urc) {
		if(params.username){
			if (urc.hasErrors()) {
				return [ user : urc]
			}
			else {
				def user = new User(params)
				user.save()
				sendMailService.sendMail(user.email,"Konto na TaskManger",'Twoje konto na TaskManger zostało założone.Czekaj na akceptacje od admina.')
				flash.message = "Konto zostało utworzone czekaj na akceptacje od admina"
				redirect (url:'/')
			}
		}
	}


	class UserRegistrationCommand {

		String username
		String password
		String password2
		String email
		String firstName
		String lastName
		static constraints = {
			importFrom User
			username (
					validator: { username, urc ->
						if(User.findByUsername(username))
							return false
					})
			password(
					validator: { passwd, urc ->
						return passwd != urc.username
					})

			password2(
					validator: { passwd2, urc ->
						return passwd2 == urc.password
					})
			firstName(blank: false)
			lastName(blank: false)
		}
	}
}
