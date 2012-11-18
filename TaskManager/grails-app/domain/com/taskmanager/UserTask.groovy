package com.taskmanager

class UserTask {
	User user
	Task task
	Date date
	boolean confirm = false
    static constraints = {
		date(nullable:true)
    }
	
}
