<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<title>TaskManager - Zadanie ${task.name} - szczegóły</title>
<g:javascript src="calendar.js" />
  <gvisualization:apiImport/>
</head>
<body>
	<hr>
	Zadanie ${task.name} - szczegóły
	<hr style="margin-bottom:10px">
	<g:if test="${task.description}"><div style="margin:auto; width:90%;">Opis: <br> ${task.description}</div></g:if>
	<div style="margin-top:10px; overflow:hidden; width:100%;">
	<div style="width:40%; float:right;height:270px;">
	<div type="text" id="datepicker"></div>
	</div>
	<div style="width:60%; margin-top:20px; float:left;">
	<div id="show" style="margin:center;">
		<g:render template="hoursPanel"
			model="${[taskRaport: taskRaport,task:task,userId:userId, wykresKolumny : wykresKolumny, wykresDane : wykresDane]}" />
	</div>
	<g:hiddenField name="taskId" id="taskId" value="${id}" />
	</div>
	</div>
	<g:if test="${tasksUser?.confirm}">
		<g:link style="float:center;" action="showHistory" class="button">Powrót</g:link>
	</g:if>
	<g:else>
	<g:link style="float:center;" controller="DeveloperPanel" action="tasksList" class="button">Powrót</g:link>
	</g:else>
	
	
</body>
</html>
