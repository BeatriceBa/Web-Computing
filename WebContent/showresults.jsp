<!DOCTYPE html>
<%@page import="java.beans.EventHandler"%>
<%@page session="false"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Risultati Ricerca</title>
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
	<%@ page import="utilities.*"%>
	<%@ page import="javax.servlet.http.HttpServlet.*"%>
	<%@ page import="javax.servlet.http.HttpSession"%>
	<%
		String term = (String) request.getParameter("adding");
		BookDescription b = new BookDescription();
		ArrayList<BookDescription> res = b.wantToAdd(term);
		HttpSession sessione = request.getSession(false);
	%>
	<jsp:include page="navbar.jsp"></jsp:include>
	<section class="section">
		<div class="container">
			<h2 class="title">Libri Ricercati in base a : <%=term%></h2>
			<%
				if (res != null) {
					for (BookDescription book : res) {
			%>
		</div>
		<div class="column">
			<div class="card">
				<div class="card-content">
					<div class="media">
						<div class="media-left">
							<figure class="image">
								<img id="image" src="<%=book.getImageUrl()%>"
									alt="Placeholder image">
							</figure>
						</div>
						<div class="media-content" id="media">
							<p class="title is-4"><%=book.getTitle()%></p>
							<p class="subtitle is-6"><%=book.getAuthor()%></p>
							<p class="lead"><%=book.getDescription()%></p>
							<b>ISBN : </b><%=book.getISBN()%>
							<br> <b>Data di Pubblicazione:</b><%=1900 + book.getYear().getYear()%>
							<br> <b>Categoria : </b><%=book.getCategory().getName()%>
							<br> <br>
							<div class="container">
								<div class="level-left">
									<p>
										<input class="input" id="copies" type="number"
											placeholder="Copie" required>
										
									</p>
									<p>
										<button class="button is-info" type="button" id="addButton"
											value="Aggiungi" isbn="<%=book.getISBN()%>"
											category="<%=book.getCategory().getName()%>"
											title="<%=book.getTitle()%>" author="<%=book.getAuthor()%>"
											publishingHouse="<%=book.getPublishingHouse()%>"
											description="<%=book.getDescription()%>"
											date="<%=book.getYear()%>"
											imageUrl="<%=book.getImageUrl()%>">
											<span>Aggiungi</span>
										</button>
									</p>

								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
		<%
			}
		}
		%>
	</section>
	<script>
		$('.button.is-info').each(function(index) {
			$(this).on("click",function() {
				$.ajax({
					url : 'ShowResults',
					type : 'post',
					data : {
						action : 'addBook',
						title : $(this).attr('title'),
						author : $(this).attr('author'),
						isbn : $(this).attr('isbn'),
						imageUrl : $(this).attr('imageUrl'),
						description : $(this).attr('description'),
						date : $(this).attr('date'),
						category : $(this).attr('category'),
						publishingHouse : $(this).attr('publishingHouse'),
						copies : document.getElementsByTagName("input")[index - 1].value
					},
					success : function(response) {
							var columnButton = document.getElementsByTagName("button")[index - 1];
							//console.log(columnButton); 
							var copy = document.getElementsByTagName("input")[index - 1];
							$value = copy.value;
							//console.log($value); 
							if (response == "NO" || copy.checkValidity()==false) {
								var element = document.getElementsByTagName("button")[index - 1];
								element.className = "button is-danger";
								element.innerHTML = "Errore";
								document.location.reload(true);
							} 
							else {
								
								var element = document.getElementsByTagName("button")[index - 1];
								element.className = "button is-success";
								element.innerHTML = "Aggiunto";
								document.location.reload(true);
							}

						}
					});
			});
	});
	</script>
</body>