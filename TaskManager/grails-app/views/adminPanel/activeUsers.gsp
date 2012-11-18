<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>Panel Admina - Aktywacja użytkowników</title>
</head>
<body>
	<hr>
	Aktywacja użytkowników
	<hr style="margin-bottom:10px">
	<g:if test="${users}">
		<table>
			<tr>
				<th style="width:180px;">Nazwa Użytkownika</th>
				<th>Email</th>
				<th>Status</th>
				<th style="width:120px;">Aktywacja</th>
			</tr>
			<g:each var="user" in="${users}">
				<g:form action="active" id="${user.id}" >
					<tr>
						<td>
							${user.username}
						</td>
						<td>
							${user.email}
						</td>
						<td><g:select name="role"
								from="${com.taskmanager.Role.list()}"
								optionKey="id" /></td>
						<td><g:submitButton  name="active" class="button" style="width:130px; background-color:#973; cursor:pointer;" value="Aktywuj"/>
						<g:link action="removeUser" id="${user.id}" class="wyczysc">
						<input style="width:130px; background-color:#973; cursor:pointer;" type="button" name="delete" value="Usuń" class="delete button" url="${createLink(uri: '/')}"/></g:link>
						</td>
					</tr>
				</g:form>

			</g:each>
		</table>
	</g:if>
	<g:else>
		Brak użytkowników do aktywacji.
	</g:else>
	<g:link controller="AdminPanel" class="button">Powrót</g:link>
</body>
</html>
