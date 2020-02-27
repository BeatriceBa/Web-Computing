<!DOCTYPE html>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Prestito</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">
	<script defer src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"
		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
		crossorigin="anonymous"></script>
	<script type="text/javascript" src="./js/cart.js" > </script>
	<%@ page import="model.*"%>
	<%@ page import="dao.jdbc.*"%>
	<%@ page import="java.util.*"%>
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	<section class="section">
		<div class="columns">
			<div class="column is-full">
				
				<c:choose>
					<c:when test="${cart.isEmpty()}">
						<h1 class="title">Il tuo carrello e' vuoto</h1> <a href="${pageContext.request.contextPath}/ShowBooks"><button class="button is-large is-success is-fullwidth">Torna alla Homepage</button></a>
					</c:when>
					
					<c:otherwise>
						<h1 class="title has-text-centered">Libri da prendere in prestito:</h1>
						<div class="container">
							<c:forEach items="${cart}" var="book">
								<c:set var="title" value="${book.getBookDescription().getTitle()}"/>
								<c:set var="author" value="${book.getBookDescription().getAuthor()}"/>
								<c:set var="description" value="${book.getBookDescription().getDescription()}"/>
								<c:set var="imageURL" value="${book.getBookDescription().getImageUrl()}"/>
								<c:set var="isbn" value="${book.getBookDescription().getISBN()}"/>
								
								<div class="notification">
									
									<article class="media">
										<figure class="media-left"> <p class="image is-64x64"> <img src="${imageURL}"> </p> </figure>
										<div class="media-content">
											<div class="content">
												<strong>${title}</strong> <small>${author}</small> <br> ${description}
											</div>
										</div>
										<div class="media-right">
											<button class="delete" id=removeButton isbn="${isbn}"></button>
										</div>
									</article>
									
								</div>		
							</c:forEach>
							<a href="Cart?Action=Checkout"><button class="button is-large is-success is-fullwidth">Prendi in prestito</button></a>
						</div>
					</c:otherwise>
					
				</c:choose>
				<br>
				<br>
			</div>
		</div>
	</section>
</body>
</html>