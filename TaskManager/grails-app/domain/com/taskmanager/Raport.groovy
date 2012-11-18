package com.taskmanager

class Raport{
	
	User creator
	SortedSet positions
	Integer weekOfYear
	Integer year
	Date dateConfirm
	Date dateConfirmByAdmin
	static hasMany = [positions:Position]
	static belongsTo = [task:Task]
	
    static constraints = {
		dateConfirm(nullable:true)
		dateConfirmByAdmin(nullable:true)
    }

	
}
