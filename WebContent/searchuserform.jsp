<%@ page language="java" contentType="text/html" charset="UTF-8"

pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Search Book</title>

</head>

<body>

<form method="post" action="SearchUserLoan">

<tr>
<td>Matricola:</td>
<td><input type="text" name="matricola" size="6" /></td>
<td><input type="submit" value="Search User" /></td>
</tr>



</form>
<tr>
<form method="post" action="OnlineArrear">
<td><input type="text" name="ArrearLoanId"/></td>

</form>
</tr>

</body>

</html>