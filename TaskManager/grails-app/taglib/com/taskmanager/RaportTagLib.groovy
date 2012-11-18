package com.taskmanager

class RaportTagLib {
	static namespace = "gMy"
	def raportActivity = {attrs ->
		out << "Użytkownik ${attrs.raport.creator.username} stworzył raport do ${attrs.raport.task.name} w dniu "	
		out << new java.text.SimpleDateFormat(attrs.format).format(attrs.raport.dateConfirm)	
	}
}
