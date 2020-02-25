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
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
</head>
<body>
	<%@ page import="dao.jdbc.*"%>
	<%@ page import="dao.*"%>

	<%@ page import="database.*"%>
	<%@ page import="model.*"%>
	<%@ page import="java.util.*"%>
	<%@ page import="java.time.LocalDate"%>
	<% 
		
		List<BookLoan> bookloanlist = (request.getAttribute("bookLoanList") != null) ? (List<BookLoan>) request.getAttribute("bookLoanList") : new LinkedList<BookLoan>();
		
		List<BookLoan> arrearLoans = new LinkedList<BookLoan>();
		for (BookLoan b : bookloanlist) {
			if (b.getArrears() > 0) {
				arrearLoans.add(b);
			}
		}
	%>
	<jsp:include page="navbar.jsp" />
	<section class="section">
		<div class="container">
			<h2 class="title">
				Prestiti in corso
				</h1>
				<div class="columns is-multiline">

					<% for (BookLoan loan : bookloanlist) {	%>
					<div class="column is-one-quarter">
						<div class="card">
							<div class="card-image">
								<figure class="image is-3by4">
									<img src=<%=loan.getBook().getBookDescription().getImageUrl() %>
										alt="Placeholder image">
								</figure>
							</div>
							<div class="card-content">
								<div class="content">
									<center>
										<p class="title is-5"><%=loan.getBook().getBookDescription().getTitle()%></p>
										<%=loan.getBook().getBookDescription().getAuthor()%>
										<% if(loan.getArrears() == 0) { %>
										</a> <a button class="button is-small" id="returnButton"
											href="${pageContext.request.contextPath}/ReturnBook?bookloanid=<%=loan.getId()%>"> Restituisci </a>
										</center>
										<% } else { %>
										<a button class="button is-primary is-small" id="PayArrear"
											href="${pageContext.request.contextPath}/PaymentProcess?LoanId=<%=loan.getId()%>"> Paga </a>
										</center>
									<% } %>
								</div>
							</div>
						</div>
					</div>
					<%
						}       
					%>

				</div>
		</div>
	</section>
</body>
</html>