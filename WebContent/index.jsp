<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Library</title>

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.4/css/bulma.min.css"
	rel="stylesheet">
<link
	href="https://demo.creativebulma.net/components/carousel//assets/css/highlight.css"
	rel="stylesheet">
<link
	href="https://demo.creativebulma.net/components/carousel//assets/css/bulma-carousel.min.css"
	rel="stylesheet">
<script defer
	src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
</head>
<body>
	<section class="hero is-info is-bold">
		<div class="hero-body">
			<div class="container ">
				<center>
					<p class="title is-1 is-spaced">Benvenuto nella tua Biblioteca Online</p>
				</center>
				<br>
				<div class="tabs is-centered">
					<ul>
						<li><a href="credits.jsp"><span class="icon is-small"><i
									class="fa fa-code" aria-hidden="true"></i></span> <span>Chi siamo</span></a></li>
						<li><a href="howTo.jsp"><span class="icon is-small"><i
									class="fa fa-cog"></i></span> <span>Servizi</span></a></li>
					</ul>
				</div>
			</div>

		</div>
	</section>
	<section>
		<div class="tile is-ancestor notification is-info">
			<div class="tile is-4 is-vertical is-parent">
				<div class="tile is-child box">
					<p class="title">Sei già registrato?</p>
					<br>
					<a button class="button is-info is-outlined is-fullwidth" href="${pageContext.request.contextPath}/Login">
						<span class="icon is-small"> <i class="fa fa-user-circle"></i>
						</span> <span>Registrati o accedi</span>
					</a>
					<br> <br>
					<p class="title">Cerchi un libro?</p>
					<br>
					<a button class="button is-info is-outlined is-fullwidth" href="${pageContext.request.contextPath}/ShowBooks">
						<span class="icon is-small"> <i class="fa fa-search"></i>
						</span> <span>Inizia la tua ricerca</span>
					</a>
				</div>

				<div class="tile is-child box">
					<p class="title">Orari di apertura </p>
					<div class="table-container">
						<table class="table is-fullwidth">
							<thead>
								<tr>
									<th>Giorno</th>
									<th>Ora</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th>Lunedì</th>
									<td>dalle 09:00 alle 19:00</td>
								</tr>
								<tr>
									<th>Martedì</th>
									<td>dalle 09:00 alle 19:00</td>
								</tr>
								<tr>
									<th>Mercoledì</th>
									<td>dalle 09:00 alle 19:00</td>
								</tr>
								<tr>
									<th>Giovedì</th>
									<td>dalle 09:00 alle 19:00</td>
								</tr>
								<tr>
									<th>Venerdì</th>
									<td>dalle 09:00 alle 19:00</td>
								</tr>
								<tr>
									<th>Sabato</th>
									<td>dalle 09:00 alle 17:00</td>
								</tr>
								<tr>
									<th>Domenica</th>
									<td>Chiuso</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="tile is-parent">
				<div class="tile is-child box">
					<center>
						<p class="title">Un posto fantastico per studiare in tranquillità</p>
					</center>
					<br>
					<figure class="image is-4by3">
						<img
							src="https://www.teamworld.it/wp-content/uploads/2019/03/libro-aperto-620x400.jpg">
					</figure>
				</div>
			</div>
		</div>
	</section>
</body>
</html>