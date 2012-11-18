<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title></title>
</head>
<body>
	<g:if test='${flash.message}'>
		<div class="message">
			${flash.message}
		</div>
	</g:if>
	<table>
		<tr>
			<th>Zadanie</th>
			<th>Kto</th>
			<th>Przydziel</th>
		</tr>

		<g:each var="mapEntry" in="${taskMap}">
			<g:form action="addTaskToUser" id="${mapEntry.key.id}">
				<tr>
					<td>
						${mapEntry.key.name}
					</td>
					<td><g:select name="user" from="${ mapEntry.value}"
							optionValue="username" optionKey="id" /></td>
					<td><g:submitButton name="add" value="Przydziel zadanie"/>
					</td>
				</tr>
			</g:form>
		</g:each>
	</table>
	
	<g:link action="index">Powrót</g:link>
</body>
</html>
