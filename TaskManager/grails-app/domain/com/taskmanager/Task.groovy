package com.taskmanager

import java.util.SortedSet;

class Task implements Comparable{
	
	String name
	String description
	Date dateCreated
	Date dateEnd
    static constraints = {
		description(nullable:true)
		dateEnd(nullable:true)
    }

	static hasMany = [raports:Raport]
	
	int compareTo(obj) {
		dateCreated.compareTo(obj.dateCreated)
	}
	
}
