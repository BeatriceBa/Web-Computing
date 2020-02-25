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
	<section class="hero is-small is-primary is-bold">
		<div class="hero-body">
			<div class="container">
				<h1 class="title">${name} ${surname}</h1>
				<h2 class="subtitle">Amministratore</h2>
			</div>
		</div>
	</section>
	<br>
	<section class="section">
		<div class="columns">
			<div id="bookAddForm" class="column is-half">
				<h5 class="title is-5">Aggiungi libro via ISBN o nome:</h5>
				<div class="field is-grouped">
					<p class="control is-expanded">
						<input class="input" id="bookInfo" type="text"
							placeholder="ISBN o nome" required>
					</p>
					<p class="control">
						<a class="button is-info" id="wantToAdd"> Aggiungi </a>
					</p>
				</div>
				<h5 class="title is-5">Rimuovi libro via ISBN:</h5>
				<div class="field is-grouped">
					<p class="control is-expanded">
						<input class="input" id="r_bookInfo" type="text"
							placeholder="ISBN"required>
					</p>
					<p class="control is-expanded">
						<input class="input" id="r_copies" type="number"
							placeholder="Copie" required>
					</p>
					
					<p class="control">
						<a id="removeFromLibrary" class="button is-danger"> Rimuovi </a>
					</p>
				</div>
				<h5 class="title is-5">Pagamento mora e restituzione libri
					utente:</h5>
				<div class="field is-grouped">
					<p class="control is-expanded">
						<input class="input" id="payMat" type="number"
							placeholder="Matricola" required>
					</p>
					<p class="control">
						<a class="button is-success" id="payArr"> Paga mora e
							restituisci </a>
					</p>
				</div>
			</div>
			<div class="column ">
				<h5 class="title is-5">Trova utente via nome e cognome:</h5>
				<div class="field is-grouped">
					<p class="control is-expanded">
						<input class="input" id="searchName" type="text"
							placeholder="Nome" required>
					</p>
					<p class="control is-expanded">
						<input class="input" id="searchSurname" type="text"
							placeholder="Cognome" required>
					</p>
					<p class="control">
						<a id="getInfo" class="button is-success"> Trova </a>
					</p>
				</div>
				<div class="container" id="userInfos"></div>
			</div>
		</div>
	</section>
	<script>
	
	function error_popup(message){
		
		error_message = "<div id='success_error' class='notification is-danger'><button id='notificationClose' class='delete'></button><strong>Errore: </strong>" + message + "</div>";
 		if ($('#success_error').length) {
			$('#success_error').replaceWith(error_message);
		} else {
			$(".columns").before(error_message);
		}
	
	}
	
	$('#wantToAdd').click(function(){
		if($('#bookInfo')[0].checkValidity()==false) 
			error_popup("Inserire qualcosa da cercare!");
		else if ($('#bookInfo')[0].checkValidity())
			location.href ="ShowResults?adding="+$('#bookInfo').val();
	});
	
	
	
	$('#removeFromLibrary').click(function(){
		if($('#r_bookInfo')[0].checkValidity()==false) 
			error_popup("Inserire isbn del libro da rimuovere!");
		else if($('#r_copies')[0].checkValidity()==false) 
			error_popup("Inserire numero di copie da rimuovere!");
		else if ($('#r_bookInfo')[0].checkValidity() && $('#r_copies')[0].checkValidity()){
			$.ajax({
				url : 'Admin',
				type : 'post',
				data: {
					action: 'removeBook',
					bookInfo: $('#r_bookInfo').val(),
					copies: $('#r_copies').val()
				},
				success : function(response) {
					if(response == "OK"){
					$('#r_bookInfo').val('');
					$('#r_copies').val('');
					$( ".columns" ).before( "<div class='notification is-primary'><button id='notificationClose' class='delete'></button><strong>Copie rimosse!</strong></div>" );
					}
					console.log("NO"); 
				}
			});
		}
			
	});
	
	
	
	$('#payArr').click(function(){
		if($('#payMat')[0].checkValidity())
			location.href = "PayArrear?matricola="+$('#payMat').val();
		else
			error_popup("Inserire matricola!");
 	});
	
	$('#getInfo').click(function(){
		if($('#searchName')[0].checkValidity() == false)
			error_popup("Inserire nome");
		else if($('#searchSurname')[0].checkValidity() == false)
			error_popup("Inserire cognome");
		else
			$.ajax({
				url : 'Admin',
				type : 'post',
				data: {
					action: 'findUser',
					name: $('#searchName').val(),
					surname: $('#searchSurname').val()
				},
				success: function(response) {
						$("#userDetail").remove();
		             	$("#userInfos").append("<h6 id='userDetail' class='title is-6'>Matricola utente: " + response + "</h6>");
		            }
			});
	});
	
	
	
	$(document).on('click', "[id^='notificationClose']", function () {	
		  (document.querySelectorAll('.notification .delete') || []).forEach(($delete) => {
			    $notification = $delete.parentNode;
			      $notification.parentNode.removeChild($notification);
			  });
	});
	
	window.addEventListener('beforeunload', function (e) {
 		  e.preventDefault();
 		  e.returnValue = '';
		});
	</script>
</body>
</html>