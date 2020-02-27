<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.beans.EventHandler"%>
<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Biblioteca</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">
<script defer
	src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>

<script type="text/javascript" src="./js/showbooks.js">
	
</script>
<meta charset="UTF-8">

<title>Biblioteca Unical</title>

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.4/css/bulma.min.css"
	rel="stylesheet">
<link
	href="https://demo.creativebulma.net/components/carousel//assets/css/highlight.css"
	rel="stylesheet">
<link
	href="https://demo.creativebulma.net/components/carousel//assets/css/bulma-carousel.min.css"
	rel="stylesheet">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
</head>
<body>

	<jsp:include page="navbar.jsp" />
	<div class="container is-static" style="width: 100%; height: 400px;">
		<section class="hero is-medium has-carousel"
			style="width: 100%; height: 400px;">
			<div class="hero-carousel" style="width: 100%; height: 400px;">
				<div class='has-background is-active'
					style="width: 100%; height: 400px;">
					<img class="is-background"
						src="https://cdn.pixabay.com/photo/2019/08/09/06/08/library-4394441_1280.jpg"
						alt="" />
				</div>
				<div class='has-background' style="width: 100%; height: 400px;">
					<img class="is-background"
						src="https://p0.pikrepo.com/preview/595/360/pile-of-black-and-gold-hardbound-books.jpg"
						alt="" />
				</div>
				<div class='has-background' style="width: 100%; height: 400px;">
					<img class="is-background"
						src="https://www.guidatorino.com/wp-content/uploads/2017/03/librerie-torino.jpg"
						alt="" />
				</div>
				<div class='has-background' style="width: 100%; height: 400px;">
					<img class="is-background"
						src="https://www.bergamonews.it/photogallery_new/images/2016/04/libri-537843.660x368.jpg"
						alt="" />
				</div>
			</div>
			<div class="hero-body"></div>
		</section>

		<script
			src="https://demo.creativebulma.net/components/carousel//assets/js/doc.js"></script>
		<script
			src="https://demo.creativebulma.net/components/carousel//assets/js/bulma-carousel.min.js"></script>
		<script
			src="https://demo.creativebulma.net/components/carousel//assets/js/main.js"></script>

		<section class="section">
			<div class="container" id="adding">
				<h2 class="title">Libri disponibili</h2>
				<div class="columns is-multiline">
					<c:forEach items="${bookDescriptions}" var="book">
						<div class="column is-one-fifth">
							<div class="card">
								<div class="card-image">
									<figure class="image is-3by4">
										<img src="${book.getImageUrl()}" alt="Placeholder image"
											style="">
									</figure>
								</div>
								<div class="card-content">
									<div class="content">
										<p class="title is-5">${book.getTitle()}</p>
										${book.getAuthor()} <br> <br> <a  class="button is-white"
											href="${pageContext.request.contextPath}/BookInfo?isbn=${book.getISBN()}">
											
											<i class="fas fa-info"></i>
											
										</a>
										<span></span>
										<c:set var="isbn" value="${book.getISBN()}" />
										<c:choose>
											<c:when test="${!isAdmin}">
												<c:choose>
													<c:when test="${copies.get(isbn) == true}">
														<a button class="button is-primary is-small"
															id="loanButton" isbn="${book.getISBN()}"
															onclick="loanButton(this)"> Prendi in prestito </a>
													</c:when>
													<c:otherwise>
														<a button disabled class="button is-primary is-small"
															id="loanButton" isbn="${book.getISBN()}"> Prendi in
															prestito </a>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<a button class="button is-info is-small" id="modifyButton"
													href="${pageContext.request.contextPath}/ModifyInfo?isbn=${book.getISBN()}">
													Modifica attributi </a>
											</c:otherwise>
										</c:choose>
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