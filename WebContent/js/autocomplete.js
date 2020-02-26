
	$( "#libro" ).keyup(function() {
		var value = $.value;
		if (value==null){
			if(document.getElementById("suggestion"))
			{document.getElementById("suggestion").remove()};
	   		return '';
			};
	});
	var xhr;
	var suggestValue;
	
	$('input[name="libro"]').autoComplete({
		minChars: 1,
		cache: false,
		source: function(term, response){
			xhr = $.getJSON('AutoComplete', { libro: term }, function(data){ response(data); });
			suggestValue = $.textContent; 
				if(suggestValue == null){
					if(document.getElementById("suggestion"))
		       		{document.getElementById("suggestion").remove()};
		       		return '';
		       	}
		    }, 
		    renderItem:  function (item, search){
		    	if(document.getElementById("suggestion"))
	       			{document.getElementById("suggestion").remove()};
	    		var t = document.createElement("option");
	    		t.id = "suggestion";
	    		t.className += "dropdown-content";
	    		t.append(item);
	    		document.getElementById("DropDown").append(t);
	    		return '';
		    }
		});

	$(document).on('click', "[id^='suggestion']", function () {
		document.getElementById("libro").value = document.getElementById("suggestion").textContent;
		document.getElementById("suggestion").remove();
	});	