/**
 * 
 */

$(document).ready(function() {
		var show = false;
		$('#loginForm').ajaxForm({
			url : 'Login',
			success : function(response) {
			if (response == "OK") {
				window.location.href = "Profile";
			} else if (response == "NO" && !show) {
				show = true;
				$("#loginForm").append("<div class='notification is-danger is-light'><strong>Errore!<br></strong>La mail e/o la password sono errate.</div>");
				}
			}
		});
});