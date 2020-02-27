/**
 * 
 */

$(document).ready(function() {
	$('#modifyForm').ajaxForm({
		success : function(response) {
			if (response == "YES") {			
				var element = document.getElementById("modifyButton");
				element.className = "button is-success";
				element.innerHTML = "Modificato Correttamente";
				//location.href =${pageContext.request.contextPath}"/ShowBooks";
			} 
			else if (response == "NO") {
				var element = document.getElementById("modifyButton");
				element.className = "button is-danger";
				element.innerHTML = "Non Modificato";
				document.location.reload(true);
			}
		}
	});
});