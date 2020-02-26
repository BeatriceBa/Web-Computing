function addBook(button,copiesNumber) {
	console.log(copiesNumber.value)
	$.ajax({
		url : 'ShowResults',
		type : 'post',
		data : {
			action : 'addBook',
			title : $(button).attr('title'),
			author : $(button).attr('author'),
			isbn : $(button).attr('isbn'),
			imageUrl : $(button).attr('imageUrl'),
			description : $(button).attr('description'),
			date : $(button).attr('date'),
			category : $(button).attr('category'),
			publishingHouse : $(button).attr('publishingHouse'),
			copies : copiesNumber.value
		},
		success : function(response) {
				var columnButton = button;
				//console.log(columnButton); 
				var copy = copiesNumber;
				//console.log($value); 
				if (response == "NO" || copy.checkValidity()==false) {
					var element = button;
					element.className = "button is-danger";
					element.innerHTML = "Errore";
					document.location.reload(true);
				} 
				else {
					
					var element = button;
					element.className = "button is-success";
					element.innerHTML = "Aggiunto";
					document.location.reload(true);
				}

			}
		});
}
				
