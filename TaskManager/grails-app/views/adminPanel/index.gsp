<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
 <title>TaskManager - Panel Admina</title>

</head>
<body>
	<div class="panel">
	<hr>
	Panel Admina
	<hr style="margin-bottom:10px">
	<g:link action="tasksList">Zadania</g:link>
	<g:link action="notConfirmRaports">Zatwierdź raporty</g:link>
	<g:link action="statMonth">Statystyka Miesięczna</g:link>
	<g:link action="activeUsers">Aktywacja użytkowników</g:link>
	<g:link controller="DeveloperPanel" action="index">Moje zadania</g:link>

	</div>
	<div class="lastActivity">
		<hr>Raporty oczekujace na potwierdzenie<hr>
			<br>
			<g:if test="${raports}">
				<g:each var="raport" in="${raports}">
					<gMy:raportActivity format="dd-MM-yyyy" raport="${raport}" />
					<br>
				</g:each>
			</g:if>
			<g:else>
				Brak raportów oczekujących za zatwierdzenie.
			</g:else>
	</div>
</body>
</html>
