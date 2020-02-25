<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">
	<script defer src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"
		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
		crossorigin="anonymous">
	</script>
	
	<script src="Charts/js/fusioncharts.js"></script>
	<script src="Charts/js/themes/fusioncharts.theme.fusion.js"></script>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	
	<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
	<% String arrearDataPoints = (String) request.getAttribute("arrear"); 
	   String loanDataPoints = (String) request.getAttribute("loan"); %>
	
	<script type="text/javascript">
		const arrearPieSource = {
			chart: {
					caption: "More degli utenti",
					subcaption: "Visualizzate in percentuale",
					showvalues: "1",
					showpercentintooltip: "0",
					numberprefix: "$",
					enablemultislicing: "1",
					theme: "fusion"
				},
			data: <%=arrearDataPoints%>
		};
		
		const arrearColumnSource = {
				chart: {
						caption: "More degli utenti",
						subcaption: "Valori effettivi",
						theme: "fusion"
					},
				data: <%=arrearDataPoints%>
		};
		
		const loanPieSource = {
				chart: {
						caption: "Prestiti degli utenti",
						subcaption: "Visualizzati in percentuale",
						showvalues: "1",
						showpercentintooltip: "0",
						numberprefix: "$",
						enablemultislicing: "1",
						theme: "fusion"
					},
				data: <%=loanDataPoints%>
			};
		
		const loanColumnSource = {
				chart: {
						caption: "Prestiti degli utenti",
						subcaption: "Valori effettivi",
						theme: "fusion"
					},
				data: <%=loanDataPoints%>
		};
		
		FusionCharts.ready(function() {
			var arrearPieChart = new FusionCharts({
				type: "pie3d",
				renderAt: "arrear-pie-chart",
				width: "100%",
				height: "100%",
				dataFormat: "json",
				dataSource : arrearPieSource
				}).render();
			
			var arrearColumnChart = new FusionCharts({
				type: "column3d",
				renderAt: "arrear-column-chart",
				width: "100%",
				height: "100%",
				dataFormat: "json",
				dataSource : arrearColumnSource
				}).render();
			
			var arrearPieChart = new FusionCharts({
				type: "pie3d",
				renderAt: "loan-pie-chart",
				width: "100%",
				height: "100%",
				dataFormat: "json",
				dataSource : loanPieSource
				}).render();
			
			var loanColumnChart = new FusionCharts({
				type: "column3d",
				renderAt: "loan-column-chart",
				width: "100%",
				height: "100%",
				dataFormat: "json",
				dataSource : loanColumnSource
				}).render();
		});	
		
	</script>
	<section>
		  <div class="columns is-multiline">
			<div class="column is-half">
				<div id="arrear-pie-chart" style="height: 400px; width: 100%;"></div>
			</div>
			<div class="column is-half">
				<div id="arrear-column-chart" style="height: 400px; width: 100%;"></div>
			</div>
			<div class="column is-half">
				<div id="loan-pie-chart" style="height: 400px; width: 100%;"></div>
			</div>
			<div class="column is-half">
				<div id="loan-column-chart" style="height: 400px; width: 100%;"></div>
			</div>
			
	 	</div>
	</section>
	
</body>
</html>   