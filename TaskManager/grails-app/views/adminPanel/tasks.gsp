<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
<g:javascript src="drop.js" />
<title>TaskManager - Zadania aktywne</title>
</head>
<body>
	<hr>
		Zadania aktywne
	<hr style="margin-bottom:10px">
		<table>
			<tr>
				<th style="width:200px;">Zadanie</th>
				<th>Użytkownicy</th>
			</tr>

			<g:each var="taskUsersEntry" in="${taskUsersMap}">
				<tr>
					<td>
					<g:link action="showTask" class="button" style="width:150px; background-color:#973;" id="${taskUsersEntry.key.id}">
						${taskUsersEntry.key.name}
						</g:link>
					</td>
					<td>
						<g:if test="${taskUsersEntry.value}">
							<g:each var="user" in="${taskUsersEntry.value}">
									${user.username};
							</g:each>
						</g:if>
						<g:else>
							Brak użytkowników przydzielonych do tego zadania
						</g:else>
					</td>
				</tr>
			</g:each>
		</table>
	<g:link action="showEndedTasks" class="button" style="width:200px;">Pokaż zadania zakończone</g:link>
	<g:link action="index" class="button">Powrót</g:link>
</body>
</html>
