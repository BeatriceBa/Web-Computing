/**
 * 
 */
$(document).on('click', "[id^='removeButton']", function () {
		    	$notification = this.parentNode;
			    $notification.parentNode.removeChild($notification);
			    var xhr = new XMLHttpRequest();
			    xhr.open('GET', "Cart?Action=RemoveFromCart&BookDescription=" + this.getAttribute("isbn") , true);
			    xhr.send();
			    document.location.reload(true);
	    
});