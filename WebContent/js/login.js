// wait for the DOM to be loaded 
$(document).ready(function() { 
	// bind 'myForm' and provide a simple callback function 
	var show = false;
	$('#loginForm')
	.ajaxForm({
		url : 'Login',
		success : function (response) {
			if(response == "OK"){
				window.location.href = "Profile";
			} else if(response == "NO" && !show){
				show = true;
				$( "#loginForm" ).append("<div class='notification is-danger is-light'><strong>Errore!<br></strong>L'utente è già registrato</div>");
			} 
		}
	});
}); 
