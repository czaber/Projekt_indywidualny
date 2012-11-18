package com.taskmanager

class SendMailService {

    def sendMail(pEmail,pSubject ,pBody) {
		runAsync{
			sendMail {
				to pEmail
				subject pSubject
				body pBody
			}
		}
		
				
		

    }
}
