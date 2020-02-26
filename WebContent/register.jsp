<!DOCTYPE html>
<%@page session = "false" %>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Registrazione</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">
<script defer
	src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script type="text/javascript" src="./js/register.js" > </script>

</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	<section class="hero is-primary is-fullheight">
		<div class="hero-body">
			<div class="container">
				<div class="columns is-centered">
					<div class="column is-5-tablet is-4-desktop is-3-widescreen">
						<form action="Register" class="box" id="registerForm" method="post">
							<div class="field">
								<label for="" class="label">Matricola</label>
								<div class="control has-icons-left">
									<input type="text" name="id" placeholder="es. 183094"
										class="input" required> <span
										class="icon is-small is-left"> <i
										class="fa fa-address-card"></i>
									</span>
								</div>
							</div>
							<div class="field">
								<label for="" class="label">Nome</label>
								<div class="control has-icons-left">
									<input type="text" name="name" placeholder="es. Davide"
										class="input" required> <span
										class="icon is-small is-left"> <i
										class="fa fa-address-card"></i>
									</span>
								</div>
							</div>
							<div class="field">
								<label for="" class="label">Cognome</label>
								<div class="control has-icons-left">
									<input type="text" name="surname" placeholder="es. Pucci"
										class="input" required> <span
										class="icon is-small is-left"> <i
										class="fa fa-address-card"></i>
									</span>
								</div>
							</div>
							<div class="field">
								<label for="" class="label">Indirizzo</label>
								<div class="control has-icons-left">
									<input type="text" name="address"
										placeholder="es. Via delle rose 21" class="input" required>
									<span class="icon is-small is-left"> <i
										class="fa fa-address-card"></i>
									</span>
								</div>
							</div>
							<div class="field">
								<label for="" class="label">Email</label>
								<div class="control has-icons-left">
									<input type="email" name="email"
										placeholder="es. dpucci@gmail.com" class="input" required>
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
							<br>
							<div class="field">
								<center>
									<label for="" class="label">Studente o Professore?</label>
								</center>
								<center>
									<div class="select">
										<select name="userType" form="registerForm">
											<option value="Student">Studente</option>
											<option value="Professor">Professore</option>
										</select>
									</div>
								</center>
							</div>
							<br>
							<div class="field">
								<center>
									<button class="button is-success" type="submit">
										Registrati</button>
								</center>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>