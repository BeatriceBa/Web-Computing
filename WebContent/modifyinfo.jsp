<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="dao.jdbc.*"%>
<%@ page import="model.*"%>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">
<script defer
	src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Modifica Attributi</title>
<%
	BookDescriptionDaoJDBC dao_bd = new BookDescriptionDaoJDBC();
	BookDescription bd = (BookDescription) dao_bd.getbyKey(request.getParameter("isbn"));
%>
<script type="text/javascript" src="./js/modifyInfo.js" > </script>
</head>
<body>


	<jsp:include page="navbar.jsp"></jsp:include>

	<section class="hero is-fullheight">
		<div class="hero-body">
			<div class="container">

				<div class="columns is-centered">
					<div class="column is-one-quarter">
						<figure class="image is-3by4">
							<img src="<%=bd.getImageUrl()%>" alt="Placeholder image">
						</figure>
					</div>
					<div class="column">
						<form action="ModifyInfo?isbn=<%=bd.getISBN()%>" class="box"
							id="modifyForm" method="post">
							<h1>
								<div class="field">
									<label for="" class="label">Titolo</label>
									<div class="control has-icons-left">
										<input type="text" name="title" value="<%=bd.getTitle()%>"
											placeholder="" class="input" required> <span
											class="icon is-small is-left"> <i
											class="fas fa-book"></i>
										</span>
									</div>
								</div>
							</h1>
							<h4>
								<div class="field">
									<label for="" class="label">Autore</label>
									<div class="control has-icons-left">
										<input type="text" name="author" value=" <%=bd.getAuthor()%>"
											placeholder="" class="input" required> <span
											class="icon is-small is-left"> <i
											class="fas fa-user"></i>
										</span>
									</div>
								</div>
							</h4>
							<p>
							<div class="field">
								<label for="" class="label">Descrizione </label>
								<div class="control has-icons-left">
									<textarea id="story" name="description" rows="5" cols="33"
										class="input" required> <%=bd.getDescription()%>
										</textarea>
									<span class="icon is-small is-left"> <i
										class="fas fa-edit"></i>
									</span>
								</div>
							</div>
							</p>
							<p>
							<div class="field">
								<label for="" class="label">Category </label>
								<div class="control has-icons-left">
									<input type="text" name="category"
										value=" <%=bd.getCategory().getName()%>" placeholder=""
										class="input" required> <span
										class="icon is-small is-left"> <i
										class="fas fa-bookmark"></i>
									</span>
								</div>
							</div>
							</p>
							<br> <b>ISBN: </b><%=bd.getISBN()%><br> <b>Data di Pubblicazione: </b> <%=bd.getYear()%><br> <br>
							<div class="field">
								<button class="button is-info" id="modifyButton" type="submit">Modifica</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>