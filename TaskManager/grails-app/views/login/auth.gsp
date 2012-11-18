<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>Witam w TaskManager</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"
	type="text/css">
</head>
<body>
	<sec:ifNotLoggedIn>
		<div id="login">
			<div id="content">
				<form action="${resource(file: 'j_spring_security_check')}"
					id='loginForm' method="post">
					<label class="login-info">Użytkownik</label> 
					<input class="input"
						name='j_username' type="text"
						onblur="if (value =='') {value = 'Nazwa użytkownika'}"
						onfocus="if (value == 'Nazwa użytkownika') {value =''}"
						type="text" value="Nazwa użytkownika" /> <label
						class="login-info">Hasło</label> <input class="input"
						name='j_password' type="password"
						onblur="if (value =='') {value = 'xxxxxxxx'}"
						onfocus="if (value == 'xxxxxxxx') {value =''}" type="password"
						value="xxxxxxxx" />
					<div id="remember-forgot">
						<input name="Submit" src="${ g.resource( dir:'images', file:'login-button.jpg' ) }" type="image"
							value="Login" />
					</div>
				</form>
			</div>
		</div>
	</sec:ifNotLoggedIn>
</body>
</html>
