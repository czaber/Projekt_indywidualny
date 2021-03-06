<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>TaskManager - Zadanie:${task} - Użytkownik: ${user} - raporty</title>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
    <script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
    <g:javascript src="tooltip.js"></g:javascript>
</head>
<body>
	<hr>
	Zadanie: ${task} - Użytkownik: ${user} - raporty
	<hr style="margin-bottom:10px">
	<div>
	<g:if test="${raports}">
		<g:render template="/templates/raports" model="raports"></g:render>
		<g:paginate total="${raportSize}" max="5" params="${[userId:userId,taskId:taskId]}"/>
		<br>
	</g:if>
	<g:else>
		<h3>Brak zatwierdzonych raportów</h3>
	</g:else>	
	<g:link action="showTask" class="button" id="${taskId}">Powrót</g:link>
	</div>

</body>
</html>
