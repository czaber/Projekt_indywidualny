<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<title>TaskManager - Dodawanie Raportów</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"
	type="text/css">
</head>
<body>
	<hr>
	Dodawanie Raportów
	<hr style="margin-bottom:10px">
	<table style="width:95%;">
		<tr>
			<td>Zadanie</td>
			<td><g:link action="addRaport"
					params="${[direction:"previous",weekNumber:weekNumber,year:year]}"><span class="ui-icon ui-icon-circle-triangle-w"></span></g:link>
			</td>
			<g:each var="day" in="${weekdays}" status="i">
				<td><g:formatDate format="E dd-MM-yyyy" date="${day}" /></td>
			</g:each>
			<td>	<g:link action="addRaport"
					params="${[direction:"next",weekNumber:weekNumber,year:year]}"><span class="ui-icon ui-icon-circle-triangle-e"></span></g:link>
			</td>
		</tr>
		<g:if test="${taskPositionsMap}">
			<g:each var="entryMap" in="${taskPositionsMap}">
				<tr>
					<td>
						${entryMap.key.name}
					</td>
					<td></td>
					<g:each var="day" in="${weekdays}" status="i">
						<td><g:textField class="hours" id="${entryMap.key.id+"_"+i}" name="hours" size="3"
								value="${entryMap?.value?.getAt(i)?.workHours }"
								onchange="${remoteFunction(action: 'addPositionAjax',  
									params:'\'taskId=' +entryMap.key.id + '&weekday=' +i + '&weekNumber=' +weekNumber + '&year=' +year+ '&changedValue=\' + this.value', update:'message')}" /></td>
					</g:each>
					<td></td>
				</tr>
			</g:each>
		</g:if>
		<g:else>
			<tr>
				<td colspan="10">W tym tygodniu raporty zostały wykonane</td>
			</tr>
		</g:else>
	</table>
	<br>
		<table class="none" style="width:40%; height:auto;"><tr><td><g:link action="confirmRaport"	class="button" params="${[weekNumber:weekNumber,year:year]}" onclick="javascript:return confirm('Na pewno chcesz zatwierdzić wszystkie raporty?')">Zatwierdź</g:link></td>
		<td><g:link controller="DeveloperPanel" class="button">Powrót</g:link></td></table>
	<div id="message" class="message"></div>
</body>
</html>
