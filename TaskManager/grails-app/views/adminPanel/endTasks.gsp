<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>TaskManager - Zakończ zadania</title>
</head>
<body>
	<hr>
		Zakończ zadania
	<hr style="margin-bottom:10px">
	<table>
		<tr>
			<th>Zadanie</th>
			<th>Developer</th>
			<th>Zakończ</th>
		</tr>

		<g:each var="mapEntry" in="${taskMap}">
			<tr>
				<td>
					${mapEntry.key.name}
				</td>
				<td>
					<table style="width:100%">
						<g:each var="userTask" in="${mapEntry.value}">
							<tr>
								<td>
									${userTask.user.username}
								</td>
								<td style="width:75px;">
								<g:if test="${userTask.date}">
									Oczekujące
								</g:if>
								<g:else>
									W trakcie 
								</g:else>	
								</td>
								<td style="width:205px;">
									<g:link class="button" style="width:200px;  background-color:#973;"action="endTaskForUser" 
									params="${[taskId: userTask.task.id,userId:userTask.user.id]}" onclick="javascript:return confirm('Na pewno chcesz zakonczyć to zadanie dla usera?')">Zakończ zadanie dla usera</g:link>
								</td>
							</tr>
						</g:each>

					</table>
				</td>
				<td><g:link class="button" style="width:120px; background-color:#973;" action="endTask" id="${mapEntry.key.id}"
				onclick="javascript:return confirm('Na pewno chcesz zakonczyć całe zadanie?')" >Zakończ zadanie</g:link>
				</td>
			</tr>
		</g:each>
	</table>

<g:link action="tasksList" class="button">Powrót</g:link>

</body>
</html>
