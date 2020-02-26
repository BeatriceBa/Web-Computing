function extension(button) {
	$.ajax({
		url : 'Extension',
		type : 'get',
		data : {
			LoanId: $(button).attr('LoanId')
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
				$('#CurrentLoans').after(message);
			}
		}
	});	

	$(document).on('click', "[id^='notificationClose']", function () {
		(document.querySelectorAll('.notification .delete') || []).forEach(($delete) => {
			$notification = $delete.parentNode;
			$notification.parentNode.removeChild($notification);
		});
	});	
}