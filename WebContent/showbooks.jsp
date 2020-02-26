<!DOCTYPE html>
<%@page import="java.beans.EventHandler"%>
<%@page session="false"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Biblioteca</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">
<script defer
	src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript" src="./js/showbooks.js" > </script>
</head>
<body>

	<%@ page import="dao.jdbc.*"%>
	<%@ page import="dao.*"%>

	<%@ page import="database.*"%>
	<%@ page import="model.*"%>
	<%@ page import="java.util.*"%>
	<%@ page import="java.time.LocalDate"%>
	<%@ page import="utilities.*"%>
	<%@ page import="javax.servlet.http.HttpServlet.*"%>
	<%@ page import="javax.servlet.http.HttpSession"%>
	<%
		final DatabaseManager dm = DatabaseManager.getInstance();
		BookDescriptionDao dao = new BookDescriptionDaoJDBC();
		List<BookDescription> res = dao.findAll();
		HashMap<String,Boolean> copies = dao.isThereABook();
		HttpSession sessione = request.getSession(false);
	%>
	<section class="section">
		<div class="container" id="adding">
			<h2 class="title">
				Libri disponibili
				</h1>
				<div class="columns is-multiline">

					<% for (BookDescription book : res) { %>
					<div class="column is-one-fifth">
						<div class="card">
							<div class="card-image">
								<figure class="image is-3by4">
									<img src="<%=book.getImageUrl()%>" alt="Placeholder image" style="width=100%; height=300px">
								</figure>
							</div>
							<div class="card-content">
								<div class="content">
									
										<p class="title is-5"><%=book.getTitle()%></p>
										<%=book.getAuthor()%>
										<br> <br> <a button class="button is-small"
											href="${pageContext.request.contextPath}/BookInfo?isbn=<%=book.getISBN()%>">
											<span class="icon is-small"> <i class="fas fa-info"></i></a> 
										</span>
										<% if (!SessionHandler.isAdmin(request)) {
												if(copies.get(book.getISBN()).equals(true)) {%>
												<a button class="button is-primary is-small" id="loanButton"
													isbn=<%=book.getISBN()%> onclick = "test()"> Prendi in prestito </a>
												
												<% } else { %>
												<a button disabled class="button is-primary is-small" id="loanButton"
													isbn=<%=book.getISBN()%> onclick ="test()"> Prendi in prestito </a> 
												<% }
										} else {%>
											<a button class="button is-info is-small" id="modifyButton"
											href="${pageContext.request.contextPath}/ModifyInfo?isbn=<%=book.getISBN()%>">
											 Modifica attributi </a>
										<%} %>
									
								</div>
							</div>
						</div>
					</div>
					<% } %>
				</div>
		</div>
	</section>
	
</body>
</html>