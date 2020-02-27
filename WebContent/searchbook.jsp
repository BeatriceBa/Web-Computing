<!DOCTYPE html>
<%@page session="false"%>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Risultati ricerca per ${searchedFor}</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">
	<script defer src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>

	<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
	<script type="text/javascript" src="./js/searchbook.js" > </script>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	<section class="section">
		<div class="container">
			<h2 class="title">Risultati ricerca per: ${searchedFor}</h2>
			<div class="columns is-multiline">
				<c:if test="${!result.isEmpty()} "></c:if>
				<c:forEach items="${result}" var="book">
					<div class="column is-one-fifth">
						<div class="card">
							<div class="card-image">
								<figure class="image is-3by4">
									<img src=${book.getImageUrl()} alt="Placeholder image">
								</figure>
							</div>
							<div class="card-content">
								<div class="content">
									<p class="title is-4">${book.getTitle()}</p>
									<p class="lead">${book.getAuthor()}</p>									
									<a button class="button is-small" href="${pageContext.request.contextPath}/BookInfo?isbn=${book.getISBN()}">
									<span class="icon is-small"> <i class="fas fa-info"></i> </span> </a>
									<c:set var="isbn" value="${book.getISBN()}"/>
									<c:choose>
										<c:when test="${!isAdmin}">
											<c:choose>
												<c:when test="${copies.get(isbn) == true}">
													<a button class="button is-primary is-small" id="loanButton" isbn="${book.getISBN()}" onclick = "loanButton(this)"> Prendi in prestito </a>
												</c:when>
												<c:otherwise>
													<a button disabled class="button is-primary is-small" id="loanButton" isbn="${book.getISBN()}"> Prendi in prestito </a> 
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<a button class="button is-info is-small" id="modifyButton" href="${pageContext.request.contextPath}/ModifyInfo?isbn=${book.getISBN()}"> Modifica attributi </a>
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