function typing (input, event){
			

	if (input.checkValidity()){
		console.log("WOW"); 
		if(document.getElementById("suggestion"))
		{document.getElementById("suggestion").remove()};
   		return '';
	}
	
	
}


$(document).on('click', "[id^='suggestion']", function () {
	document.getElementById("libro").value = document.getElementById("suggestion").textContent;
	document.getElementById("suggestion").remove();
});	