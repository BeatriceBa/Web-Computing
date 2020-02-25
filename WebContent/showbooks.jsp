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
													isbn=<%=book.getISBN()%>> Prendi in prestito </a>
												<% } else { %>
												<a button disabled class="button is-primary is-small" id="loanButton"
													isbn=<%=book.getISBN()%>> Prendi in prestito </a> 
												<% }
										} else {%>
											</a> <a button class="button is-info is-small" id="modifyButton"
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
	<script>
	$('.button.is-primary.is-small').each(function(index) {
		$(this).on("click", function(){
			$.ajax({
				url : 'Cart',
				type : 'get',
				data: {
					Action: "AddToCart",
					BookDescription: $(this).attr('isbn')
				},
				success : function(response) {
					var message="";
					if (response == "NO_USER"){
						message = "<div id='success_error' class='notification is-danger'><button id='notificationClose' class='delete'></button><strong>Errore!</strong> Effettua il login prima di prendere in prestito!</div>" ;
					} else if(response == "IN_CART"){
						message = "<div id='success_error' class='notification is-danger'><button id='notificationClose' class='delete'></button><strong>Errore!</strong> Il libro è già stato aggiunto al carrello</div>" ;
					} else if (response == "LIMIT"){
						message = "<div id='success_error' class='notification is-danger'><button id='notificationClose' class='delete'></button><strong>Errore!</strong> Hai raggiunto il limite tra quelli in prestito e quelli nel carrello</div>" ;
					} else if(response == "ADDED"){
						message = "<div id='success_error' class='notification is-primary'><button id='notificationClose' class='delete'></button><strong>Fatto!</strong> Il libro è stato aggiunto al carrello</div>" ;
					} else if(response == "NO_ID"){
						message = "<div id='success_error' class='notification is-danger'><button id='notificationClose' class='delete'></button><strong>Errore!</strong> Non ci sono copie disponibili</div>" ;	
					} else if(response == "CURRENT"){
						message = "<div id='success_error' class='notification is-danger'><button id='notificationClose' class='delete'></button><strong>Errore!</strong> Hai già questo libro </div>" ;	
					} else if(response == "ARREAR"){
						message = "<div id='success_error' class='notification is-danger'><button id='notificationClose' class='delete'></button><strong>Errore!</strong> Prima di prendere in prestito un libro paga la mora </div>" ;	
					}
				
					if ($('#success_error').length) {
						$('#success_error').replaceWith(message);
					} else {
						$('#adding').before(message);
					}
				}
			});	
		});
	});

	$(document).on('click', "[id^='notificationClose']", function () {
		
		  (document.querySelectorAll('.notification .delete') || []).forEach(($delete) => {
			    $notification = $delete.parentNode;

			      $notification.parentNode.removeChild($notification);
			  });
	    
	});
	</script>
</body>
</html>