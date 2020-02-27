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
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<section class="section">
		<div class="container">
			<h2 class="title"> Prestiti in corso </h2>
			<div class="columns is-multiline">
				<c:forEach items="${bookLoanList}" var="loan">
					<div class="column is-one-fifth">
						<div class="card">
							<div class="card-image">
								<figure class="image is-3by4"> 
									<img src=${loan.getBook().getBookDescription().getImageUrl()} alt="Placeholder image">
								</figure>
							</div>
							<div class="card-content">
								<div class="content">
									<center>
										<p class="title is-5">${loan.getBook().getBookDescription().getTitle()}</p>
										${loan.getBook().getBookDescription().getAuthor()}
										<c:choose>
											<c:when test="${loan.getArrears() == 0}">
												<a button class="button is-small" id="returnButton" href="${pageContext.request.contextPath}/ReturnBook?bookloanid=${loan.getId()}"> Restituisci </a>
											</c:when>
											<c:otherwise>
												<a button class="button is-primary is-small" id="PayArrear" href="${pageContext.request.contextPath}/PaymentProcess?LoanId=${loan.getId()}"> Paga </a>
											</c:otherwise>
										</c:choose>
									</center>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>
</body>
</html>