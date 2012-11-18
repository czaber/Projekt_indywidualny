<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>TaskManager - Zadania zakończone</title>
</head>
<body>
	<hr>
	Zadania zakończone
	<hr style="margin-bottom:10px">
	<table style="width:500px;">
		<tr>
			<th>Zadanie</th>
			<th>Zakończone</th>
		</tr>
		<g:each var="taskUser" in="${tasksUser}">
				<tr>
					<td><g:link class="button" style="width:200px; background-color:#973;" action="showDetails" id="${taskUser.task.id }" >
						${taskUser.task.name}</g:link>
					</td>
					<td>
						${taskUser.date}
					</td>
				</tr>
		</g:each>
	</table>

	<g:link action="tasksList" class="button">Powrót</g:link>
</body>
</html>
