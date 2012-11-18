<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>TaskManager - Rejestracja</title>
</head>
<body>
	<div id="create-secUser" class="content scaffold-create" role="main">
			<hr>
		Rejestracja
		<hr style="margin-bottom:10px">
		<g:hasErrors>
			<div class="errors">
				<g:renderErrors bean="${user}" as="list" />
			</div>
		</g:hasErrors>

		<g:form action="index">
			<table>
				<tr>
					<th class="rejestracja">Nazwa użytkownika:</th>
					<td class="rejestracja"><g:textField name="username" value="${user?.username}" /></td>
				</tr>
				<tr>
					<th class="rejestracja">Hasło:</th>
					<td class="rejestracja"><g:passwordField name="password" value="${user?.password}" /></td>
				</tr>
				<tr>
					<th class="rejestracja">Powtórz hasło:</th>
					<td class="rejestracja"><g:passwordField name="password2"
							value="${user?.password2}" /></td>
				</tr>
				<tr>
					<th class="rejestracja">Imie:</th>
					<td class="rejestracja"><g:textField name="firstName" value="${user?.firstName}" /></td>
				</tr>
				<tr>
					<th class="rejestracja">Nazwisko:</th>
					<td class="rejestracja"><g:textField name="lastName" value="${user?.lastName}" /></td>
				</tr>
				<tr>
					<th class="rejestracja">Email:</th>
					<td class="rejestracja"><g:textField name="email" value="${user?.email}" /></td>
				</tr>
				<tr class="buttons">
					<td class="rejestracja">
							<g:submitButton style="width: 50%; background-color:#973; cursor:pointer;" class="save button" name="rejestruj" value="Rejestracja" />
					</td>
					<td class="rejestracja">
						<g:link class="wyczysc"><input style="width: 50%; background-color:#973; cursor:pointer;" type="button" name="cancel" value="Wyczyść" class="delete button" url="${createLink(uri: '/')}"/></g:link>
					</td>
				</tr>
				
			</table>
		</g:form>
	</div>

</body>
</html>
