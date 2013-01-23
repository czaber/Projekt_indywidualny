<!DOCTYPE html>
<html lang="pl" class="no-js">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title><g:layoutTitle default="Grails" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"
	type="text/css">
<r:layoutResources />
<g:layoutHead />
</head>
<div class=page-wrapper>
<div class=page-top>

<div class="menu">
	<div class="navigation">
		<sec:ifNotLoggedIn>
			<dl class='home'>
				<dt>
					<g:link url="${createLink(uri: '/')}">Strona główna</g:link>
				</dt>
				<dd></dd>
			</dl>
		</sec:ifNotLoggedIn>
		<sec:ifLoggedIn>
			<sec:ifAnyGranted roles="ROLE_ADMIN">
				<dl class='admin'>
					<dt>
						<g:link controller='AdminPanel'>Admin</g:link>
					</dt>
					<dd>
					<g:link controller='AdminPanel' action="tasksList">Zadania</g:link>
					<g:link controller='AdminPanel' action="notConfirmRaports">Zatwierdź Raporty</g:link>
					<g:link controller='AdminPanel' action="statMonth">Statystyka</g:link>
					<g:link controller='AdminPanel' action="activeUsers">Aktywacja</g:link>
					<g:link controller='DeveloperPanel' action="index">Moje Zadania</g:link>
					</dd>
				</dl>
			</sec:ifAnyGranted>
				<dl class='develop'>
					<dt>
						<g:link controller='DeveloperPanel'>Developer</g:link>
					</dt>
					<dd>
						<g:link controller='DeveloperPanel' action="tasksList">Zadania</g:link>
						<g:link controller='DeveloperPanel' action="addRaport">Dodaj Raport</g:link>
						<g:link controller='DeveloperPanel' action="statMonth">Statystyka</g:link>
					</dd>
				</dl>
			<dl class='log'>
				<dt>
					<g:link controller="logout">Wyloguj się</g:link>
				</dt>
			</dl>
		</sec:ifLoggedIn>

		<sec:ifNotLoggedIn>

			<dl class='log'>
				<dt>
					<g:link controller="register">Zarejestruj się</g:link>
				</dt>
				<dd></dd>
			</dl>
			<dl class='log'>
				<dt>
					<g:link controller="login">Zaloguj</g:link>
				</dt>
			</dl>
		</sec:ifNotLoggedIn>
		<div class="title">Task Manager</div>
	</div>
</div>
</div>
<div class="page-main">
	<g:if test='${flash.message}'>
		<div class="message">
			${flash.message}
		</div>
	</g:if>
<sec:ifLoggedIn>
	<div class="page-middle">
	
			Witaj <sec:username />! 
	
	</div>
</sec:ifLoggedIn>
<g:layoutBody />
</div>
</div>
<div class="page-bottom">
<div class="footer">Projekt indywidualny - Adam Kuchar</div>
</div>
<g:javascript library="application" />
<r:layoutResources />
</body>
</html>
