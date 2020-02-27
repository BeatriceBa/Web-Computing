<!DOCTYPE html>
<%@page session="false"%>
<html>

<%
	HttpSession sessione = request.getSession(false);
%>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">
<script defer
	src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script type="text/javascript" src="./js/login.js" > </script>
<style>
body {
	overflow-y: hidden;
	overflow-x: hidden;
}
</style>
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	<section class="hero is-primary is-fullheight">
		<div class="hero-body">
			<div class="container">
				<div class="columns is-centered">
					<div class="column is-5-tablet is-4-desktop is-3-widescreen"
						id="loginclass">
						<form method="post" class="box" action="Login" id="loginForm">
							<div class="field">
								<label for="" class="label">Email</label>
								<div class="control has-icons-left">
									<input type="email" name="email"
										placeholder="e.g. bobsmith@gmail.com" class="input" required>
									<span class="icon is-small is-left"> <i
										class="fa fa-envelope"></i>
									</span>
								</div>
							</div>
							<div class="field">
								<label for="" class="label">Password</label>
								<div class="control has-icons-left">
									<input type="password" name="password" placeholder="*******"
										class="input" required> <span
										class="icon is-small is-left"> <i class="fa fa-lock"></i>
									</span>
								</div>
							</div>
							<div class="field">
								<button class="button is-success" type="submit">Accedi
								</button>
							</div>
						</form>
						<center>
							<h4 class="title is-4">Non registrato?</h4>
							<a href="Register"><button
									class="button is-success is-inverted">Clicca qui per
									registrarti</button></a>
						</center>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>
