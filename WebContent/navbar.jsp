<!DOCTYPE html>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ page session="false"%>
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css" />
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-autocomplete/1.0.7/jquery.auto-complete.js"
		integrity="sha256-K3qK8ynOxhJVloLac0CTWwr7iFKVDZF4Gd2yEsiAZYA="
		crossorigin="anonymous">
	</script>
	<script type="text/javascript" src="./js/navbar.js" > </script>
</head>

<body onload = "init()">
	<nav class="navbar" role="navigation" aria-label="main navigation">
		<div class="navbar-brand">
			<a class="navbar-item" href="."> <!-- <img src="https://bulma.io/images/bulma-logo.png" width="112" height="28"> -->
				<i class="fas fa-book-open fa-2x"></i>
			</a>
	
			<%@ page import="dao.jdbc.*"%>
			<%@ page import="model.*"%>
			<%@ page import="utilities.*"%>
			<%@ page import="javax.servlet.http.HttpServlet.*"%>
			<%@ page import="javax.servlet.http.HttpSession"%>
	
			<a role="button" class="navbar-burger burger" aria-label="menu"
				aria-expanded="false" data-target="navbar_basic"> <span
				aria-hidden="true"></span> <span aria-hidden="true"></span> <span
				aria-hidden="true"></span>
			</a>
		</div>
	
		<div id="navbar_basic" class="navbar-menu">
			<div class="navbar-start">
				<form class="navbar-item" action="SearchBook" method="post">
					<div class="dropdown is-active">
						<div class="dropdown-trigger">
							<div class="field is-grouped">
								<p class="control is-expanded">
									<input class="input select" autocomplete=off id="libro"
										name="libro" placeholder="Cerca per nome, autore, ecc.." />
								</p>
								<p class="control">
									<button class="button is-primary" type="submit" id=Search>Cerca</button>
								</p>
								<div class="dropdown-menu" id="DropDown" role="menu"></div>
							</div>
						</div>
					</div>
				</form>
			</div>
	
		</div>
		<br>
	
		<script>
	  
			</script>
		<%
			if (SessionHandler.getUserID(request) == -1) {
		%>
		<div class="navbar-end">
			<form class="navbar-item" action="LoginInfo">
				<div class="buttons">
					<a class="button is-primary" href="Login">
						<center>
							<strong>Registrati o Accedi</strong>
						</center>
					</a>
				</div>
			</form>
		</div>
		</div>
		<%
			} else {
		%>
		<div class="navbar-end">
			<form class="navbar-item" action="LoginInfo">
				<div class="buttons">
					<a class="button is-info" href="Profile">
						<center>
							<i class="fas fa-user"></i>
						</center>
					</a>
					<%
						if (!SessionHandler.isAdmin(request)) {
					%>
					<a class="button is-info" href="Cart">
						<center>
							<i class="fas fa-shopping-cart"></i>
						</center>
					</a>
					<%
						} else {
					%>
					<a class="button is-info" href="Charts">
						<center>
							<i class="fas fa-chart-pie"></i>
						</center>
					</a>
					<%
						}
					%>
	
					<a class="button is-danger" href="Logout">
						<center>
							<strong><i class="fas fa-sign-out-alt"></i> Logout</strong>
						</center>
					</a>
				</div>
			</form>
		</div>
		<%
			}
		%>
	</nav>
	<script type="text/javascript">
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
	</script>
</body>
