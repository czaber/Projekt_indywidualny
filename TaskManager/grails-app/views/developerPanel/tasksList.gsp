<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>TaskManager - Zadania aktywne</title>
</head>
<body>
	<hr>
	Zadania aktywne
	<hr style="margin-bottom:10px">
	<table style="width:500px;">
		<tr>
			<th style="width:250px;">Zadanie</th>
			<th>Zakończ</th>
		</tr>
		<g:each var="taskUser" in="${tasksUser}">
				<tr>
					<td>
						<g:link class="button" style="width:200px; background-color:#973;" action="showDetails" id="${taskUser.task.id}" >
						${taskUser.task.name}</g:link>
					</td>

					<td>
						<g:if test="${taskUser.date}">
								Oczekiwane na zgode admina
						</g:if> 
						<g:else>
							<g:link class="button" style="width:200px;" action="endTask" id="${taskUser.id}" onclick="javascript:return confirm('Na pewno chcesz zakończyć to zadanie?')">Zakończ</g:link>
						</g:else>
					</td>
				</tr>
		</g:each>
	</table>
	<g:link action="showHistory" class="button" style="width:200px;">Pokaż zadania zakończone</g:link>
	<g:link controller="DeveloperPanel" class="button">Powrót</g:link>
</body>
</html>
