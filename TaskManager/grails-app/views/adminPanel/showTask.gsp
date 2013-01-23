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
	<hr style="margin-bottom:10px;">
	<g:if test="${task.description}"><div style="margin:auto; width:90%;">Opis: <br> ${task.description}</div></g:if>
	<div id="datepicker" style="margin-top:20px; margin-bottom:20px; height:260px;"></div>
	<div id="show">
		<g:render template="hoursPanel"
			model="${[raportsUser: raportsUser]}" />
	</div>


	<g:hiddenField name="taskId" id="taskId" value="${id}" />
	<g:if test="${task.dateEnd }">
		<g:link action="showEndedTasks" class="button">Powrót</g:link>
	</g:if>
	<g:else>
		<g:link action="tasks" class="button">Powrót</g:link>
	</g:else>
</body>
</html>
