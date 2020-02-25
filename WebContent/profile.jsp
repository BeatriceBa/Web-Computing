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
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	<%@ page import="model.*"%>
	<%@ page import="dao.*"%>
	<%@ page import="dao.jdbc.*"%>
	<%@ page import="java.util.*"%>
	<%
		int user_id = (Integer) request.getAttribute("user_id");
		BookLoanDao book_loan_dao = new BookLoanDaoJDBC();

		List<BookLoan> everyLoan = book_loan_dao.findAll();
		List<BookLoan> returnedLoans = new LinkedList<BookLoan>();
		List<BookLoan> currentLoans = new LinkedList<BookLoan>();

		for (BookLoan b : everyLoan) {
			if (b.isReturned() && b.getUser_id() == user_id) {
				returnedLoans.add(b);
			} else if (!b.isReturned() && b.getUser_id() == user_id) {
				currentLoans.add(b);
			}
		}
	%>
	<section class="hero is-small is-primary is-bold">
		<div class="hero-body">
			<div class="container">
				<h1 class="title">${user_name} ${user_surname}</h1>
				<h2 class="subtitle">${user_type}</h2>
			</div>
		</div>


	</section>
	<section class="section">
		<h3 class="title is-3">Informazioni</h3>

		<div class="tile is-ancestor">
			<div class="tile is-4">
				<div class="tile">
					<div class="tile is-parent">
						<%
							UserDao user = new UserDaoJDBC();
							User u = user.getbyKey(user_id);
							if (!user.isMoroso(u)) {
						%>
						<article class="tile is-child notification is-success">
							<p class="title">Tutto okay!</p>
							<p class="subtitle">Non hai libri in ritardo per la
								restituzione o mora da pagare</p>
						</article>
						<%
							} else {
						%>
						<article class="tile is-child notification is-danger">
							<p class="title">Attenzione!</p>
							<p class="subtitle">
								La mora ammonta a
								<%=book_loan_dao.getTotalArrearsbyUser(u)%>
								€
							</p>
						</article>
						<%
							}
						%>
					</div>
				</div>
			</div>

			<div class="tile is-parent">
				<article class="tile is-child notification">
					<p class="title">Hai preso in prestito ${users_book} libri</p>
					<div class="content">
						<%
							for (BookLoan book : currentLoans) {
								String title = book.getBook().getBookDescription().getTitle();
								String author = book.getBook().getBookDescription().getAuthor();
								String return_date = book.getReturn_date().toString();
								int loanId = book.getId();
						%>
						<p class="subtitle">
							<i class="fas fa-book"></i>
							<%=title%>
							di
							<%=author%>
							da restituire il <strong><%=return_date%></strong> &nbsp;
							<%
								if (book.getArrears() > 0) {
							%>
							<br>Mora da pagare: <strong><%=book.getArrears()%>€</strong>
						<form action="PaymentProcess?id=<%=book.getId()%>" method="POST">
							<script src="https://checkout.stripe.com/checkout.js"
								class="stripe-button"
								data-key="pk_test_lM0LpViawa5I6QswMtG7ck0X00AFLMkrjP"
								data-amount="" data-name="Pagamento Mora"
								data-description="<%=book.getBook().getBookDescription().getTitle()%>"
								data-image="https://stripe.com/img/documentation/checkout/marketplace.png"
								data-locale="auto" data-currency="eur">
								
							</script>

						</form>
						<% } else { %>
						<a button class="button is-small is-rounded" id="extendButton" LoanId=<%=book.getId()%>>
							Proroga restituzione</a>
						<% } %>
						</p>
						<% } %>
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
						<%
							for (BookLoan book : returnedLoans) {
								String title = book.getBook().getBookDescription().getTitle();
								String author = book.getBook().getBookDescription().getAuthor();
								String loan_date = book.getLoan_date().toString();
						%>
						<p class="subtitle">
							<i class="fas fa-book"></i>
							<%=title%>
							di
							<%=author%>
							preso in prestito il <strong><%=loan_date%></strong>
						</p>
						<%
							}
						%>
					</div>
			</div>
		</div>

	</section>
<script>
$('.button.is-small.is-rounded').each(function(index) {
	$(this).on("click", function(){
		$.ajax({
			url : 'Extension',
			type : 'get',
			data : {
				LoanId: $(this).attr('LoanId')
			},
			success : function(response) {
				var message="";
				
				if(response == "NO_PROROGA"){
					message = "<div id='success_error' class='notification is-danger'><button id='notificationClose' class='delete'></button><strong>Errore!</strong> Non puoi richiedere la proroga</div>" ;
				} else if (response == "OK") {
					window.location.reload();
				}
			 	
				if ($('#success_error').length) {
					$('#success_error').replaceWith(message);
				} else {
					$(".content").after(message);
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