package com.taskmanager

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletResponse
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class CheckUserController {

   	def springSecurityService
    def index() { }
	def check() {
		
		if (SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")){
			redirect controller:'adminPanel'
		}
		else if(SpringSecurityUtils.ifAllGranted("ROLE_DEVELOPER")){
			redirect controller:'developerPanel'
		}			
	}
	
	def failAuth(){
		def username = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
		String msg = ''
		def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
		if (exception) {
				msg = g.message(code: "springSecurity.errors.login.fail")
		}
		
		flash.message = msg
		redirect(uri:'/')

		
	}
}
