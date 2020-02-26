/**
 * 
 */
function loanButton(button){
	console.log(button); 
	$.ajax({
		url : 'Cart',
		type : 'get',
		data: {
			Action: "AddToCart",
			BookDescription: $(button).attr('isbn')
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
					$(".container").before(message);
				}
			}
	});	
}

$(document).on('click', "[id^='notificationClose']", function () {
	
	  (document.querySelectorAll('.notification .delete') || []).forEach(($delete) => {
		    $notification = $delete.parentNode;

		      $notification.parentNode.removeChild($notification);
	  });
    
});