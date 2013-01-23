<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>Panel Admina - Tworzenie zadania</title>
</head>
<body>
	<g:hasErrors>
		<div class="errors">
			<g:renderErrors bean="${task}" as="list" />
		</div>
	</g:hasErrors>
	<hr>
	Tworzenie nowego zadania
	<hr style="margin-bottom:10px">
	<g:form action="createTask">
		<table>
			<tr>
				<th class="rejestracja">Nazwa zadania:</th>
				<td><g:textField name="name" value="${task?.name}" style="width: 80%;"/></td>
			</tr>
			<tr>
				<th class="rejestracja">Opis zadania:</th>
				<td><g:textArea name="description" value="${task?.description}" style="width: 248px; min-width: 248px; max-width: 248px;"/></td>
			</tr>
			<tr>
				<td colspan=2><g:submitButton class="button" name="create" value="Stwórz" style="width: 50%; background-color:#973; cursor:pointer;"/></td>
			</tr>
		</table>
	</g:form>
	
	<g:link action="tasksList" class="button">Powrót</g:link>
</body>
</html>
