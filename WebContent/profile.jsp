<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Profilo</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">
<script defer
	src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
<script type="text/javascript" src="./js/profile.js" > </script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	<section class="hero is-small is-primary is-bold">
		<div class="hero-body">
			<div class="container">
				<h1 class="title">${user.getName()} ${user.getSurname()}</h1>
				<h2 class="subtitle">${user.getUser_type()}</h2>
			</div>
		</div>
	</section>
	<section class="section">
		<h3 class="title is-3">Informazioni</h3>
		<div class="tile is-ancestor">
			<div class="tile is-4">
				<div class="tile">
					<div class="tile is-parent">
						<c:choose>
							<c:when test="${!isMoroso}">
								<article class="tile is-child notification is-success">
									<p class="title">Tutto okay!</p>
									<p class="subtitle">Non hai libri in ritardo per la restituzione o mora da pagare</p>
								</article>
							</c:when>				
							<c:otherwise>
								<article class="tile is-child notification is-danger">
									<p class="title">Attenzione!</p>
									<p class="subtitle"> La mora ammonta a ${totalArrearsPerUser} € </p>
								</article>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>

			<div class="tile is-parent">
				<article class="tile is-child notification">
					<p class="title">Hai preso in prestito ${users_book} libri</p>
					<div class="content" id="CurrentLoans">
						<c:forEach items="${currentLoans}" var="book">
							<c:set var="title" value="${book.getBook().getBookDescription().getTitle()}"/>
							<c:set var="author" value="${book.getBook().getBookDescription().getAuthor()}"/>
							<c:set var="return_date" value="${book.getReturn_date().toString()}"/>
							
							<p class="subtitle"> <i class="fas fa-book"></i> ${title} di ${author} da restituire il <strong>${return_date}</strong> &nbsp; </p>
							<c:choose>
								<c:when test="${book.getArrears() > 0}">
									<br> Mora da pagare: <strong>${book.getArrears()}€</strong>
									<form action="PaymentProcess?id=${book.getId()}" method="POST">
										<script src="https://checkout.stripe.com/checkout.js"
										class="stripe-button"
										data-key="pk_test_lM0LpViawa5I6QswMtG7ck0X00AFLMkrjP"
										data-amount="" data-name="Pagamento Mora"
										data-description="${title}"
										data-image="https://stripe.com/img/documentation/checkout/marketplace.png"
										data-locale="auto" data-currency="eur">
										</script>
									</form>
								</c:when>
								
								<c:otherwise>
									<a button class="button is-small is-rounded" id="extendButton" LoanId=${book.getId()} onclick = "extension(this)"> Proroga restituzione</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						
					</div>
				</article>
			</div>
		</div>
		<br>

		<div class="tile is-12">
			<div class="tile">
				<article class="tile  is-child notification is-info">
					<p class="title">Storico libri</p>
					<div class="content">
						<c:forEach items="${returnedLoans}" var="book">
							<c:set var="title" value="${book.getBook().getBookDescription().getTitle()}"/>
							<c:set var="author" value="${book.getBook().getBookDescription().getAuthor()}"/>
							<c:set var="loan_date" value="${book.getLoan_date().toString()}"/>
							<p class="subtitle"> <i class="fas fa-book"></i> ${title} di ${author} preso in prestito il <strong>${loan_date}</strong>
						</p>
						</c:forEach>
					</div>
			</div>
		</div>

	</section>
</body>
</html>