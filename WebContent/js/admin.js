/**
 * 
 */
	function error_popup(message){
		
		error_message = "<div id='success_error' class='notification is-danger'><button id='notificationClose' class='delete'></button><strong>Errore: </strong>" + message + "</div>";
 		if ($('#success_error').length) {
			$('#success_error').replaceWith(error_message);
		} else {
			$(".columns").before(error_message);
		}
	
	}

	function wantToAdd(){
		if($('#bookInfo')[0].checkValidity()==false) 
			error_popup("Inserire qualcosa da cercare!");
		else if ($('#bookInfo')[0].checkValidity())
			location.href ="ShowResults?adding="+$('#bookInfo').val();
	}
	
	function removeFromLibrary(){
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
			
	}
	

	function payArr(){
		if($('#payMat')[0].checkValidity())
			location.href = "PayArrear?matricola="+$('#payMat').val();
		else
			error_popup("Inserire matricola!");
	}
	
	function getInfo(){
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
	}
	
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
	
	
	
	