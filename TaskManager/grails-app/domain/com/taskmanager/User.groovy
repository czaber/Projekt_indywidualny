package com.taskmanager

import java.util.SortedSet;

class User implements Comparable{
	
	transient springSecurityService

	String username
	String password
	String email
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	String firstName
	String lastName
	static constraints = {
		username blank: false, unique: true
		password blank: false
		email blank: false , email: true
	}
	
	static hasMany = [raports:Raport]
	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
	String toString(){
		username
	  }
	
	int compareTo(obj) {
		return username.compareTo(obj.username)
	}
}
