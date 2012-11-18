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
		<table>
			<tr>
				<th>Zadanie</th>
				<th>Zakończono</th>
			</tr>

			<g:each var="task" in="${taskList}">
				<tr>
					<td><g:link action="showTask" class="button" style="width:150px; background-color:#973;" id="${task.id}">
						${task.name}</g:link>
					</td>
					<td>
						${task.dateEnd}
					</td>
				</tr>
			</g:each>
		</table>
		
		<g:link action="tasks" class="button">Powrót</g:link>
</body>
</html>
