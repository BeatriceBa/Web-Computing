<!DOCTYPE html>
<%@page session="false"%>
<html>

<%
	HttpSession sessione = request.getSession(false);
	if (sessione == null) {;} else {
		System.out.println(sessione.getCreationTime());
	}
%>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Risultati ricerca per ${searchedFor}</title>
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
	<%@ page import="model.*"%>
	<%@ page import="java.util.*"%>
	<%@ page import="dao.jdbc.*"%>
	<%@ page import="dao.*"%>
	<%@ page import="utilities.*"%>
	<%@ page import="javax.servlet.http.HttpServlet.*"%>
	<%@ page import="javax.servlet.http.HttpSession"%>
	<jsp:include page="navbar.jsp"></jsp:include>
	<section class="section">
		<div class="container">
			<h2 class="title">Risultati ricerca per: ${searchedFor}</h2>
			<div class="columns is-multiline">
				<%
					BookDescriptionDao db = new BookDescriptionDaoJDBC();
					LinkedList<BookDescription> bookD = db.getbyTitle((String) request.getAttribute("searchedFor"));
					//System.out.println(bookD.size());
					if (bookD != null) {
						for (BookDescription book : bookD) {
				%>
				<div class="column is-one-fifth">
					<div class="card">
						<div class="card-image">
							<figure class="image is-3by4">
								<img src=<%=book.getImageUrl()%> alt="Placeholder image">
							</figure>
						</div>
						<div class="card-content">
							<div class="content">
								<p class="title is-4"><%=book.getTitle()%></p>
								<p class="lead"><%=book.getAuthor()%></p>
								<center>
									<a button class="button is-small"
										href="${pageContext.request.contextPath}/BookInfo?isbn=<%=book.getISBN()%>">
										<span class="icon is-small"> <i class="fas fa-info"></i>
									</span> <% if (!SessionHandler.isAdmin(request)) { %>
									</a> <a button class="button is-primary is-small" id="loanButton"
										isbn=<%=book.getISBN()%>> Prendi in prestito </a>
									<%
										} else {
									%>
									</a> <a button class="button is-info is-small" id="modifyButton"
										href="${pageContext.request.contextPath}/ModifyInfo?isbn=<%=book.getISBN()%>">
										Modifica attributi </a>
									<%
										}
									%>
								</center>
							</div>
						</div>
					</div>
				</div>
				<%
					}
					}
				%>
			</div>
		</div>
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
						if (response == "IS_ADMIN"){
							message = "<div id='success_error' class='notification is-danger'><button id='notificationClose' class='delete'></button><strong>Errore!</strong> Un amministratore non può prendere in prestito libri!</div>" ;
						} else if (response == "NO_USER"){
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
							$(".container").before(message);
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
	</section>

</body>
</html>