package com.taskmanager

class Position implements Comparable{
	
	Integer workHours
	Date date
	String description
	
    static constraints = {
		description(nullable:true,blank:true,size:0..200)
    }
	
	static belongsTo = [Raport]

   int compareTo(obj) {
       date.compareTo(obj.date)
   }


}
