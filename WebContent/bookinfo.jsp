<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page session="false"%>
	
<!DOCTYPE html>
<html>

<%
	HttpSession sessione = request.getSession(false);
	if (sessione == null) {;} else {
		System.out.println(sessione.getCreationTime());
	}
%>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${title}-${author}</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">
<script defer
	src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
<script type="text/javascript" src="./js/bookinfo.js" > </script>
	<%@ page import="utilities.*"%>
	<%@ page import="javax.servlet.http.HttpServlet.*"%>
	<%@ page import="javax.servlet.http.HttpSession"%>
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	<section class="section">
		<div class="container">
			<div class="columns">
				<div class="column is-one-quarter">
					<figure class="image is-4by5">
						<img src="${image}">
					</figure>
				</div>
				<div class="column">
					<h1 class="title">${title}</h1>
					<h4 class="subtitle">${author}</h4>
					<p class="lead">${description}</p>
					<br> <b>ISBN: </b>${isbn}<br> <b>Data di
						Pubblicazione: </b>${date}<br> <b>Categoria: </b>${category}</br>
					<br>
					<% if(SessionHandler.isAdmin(request)){%>
						<a button class="button is-info is-medium" 
						href="${pageContext.request.contextPath}/ModifyInfo?isbn=${isbn}"
						id="modifyButton">Modifica attributi </a>
					<%} else {%>	
					
					 <a button class="button is-primary is-medium" isbn="${isbn}"
						id="loanButton" onclick="loanButton(this)">Prendi in prestito </a>
					<%} %>
				</div>

			</div>
		</div>
	</section>
	
</body>
</html>