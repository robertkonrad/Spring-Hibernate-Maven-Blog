<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title></title>
</head>
<body>
<h1>Register form</h1>

<form:form action="saveUser" method="post" modelAttribute="user">
	<table>
		<tr>
			<td>Username:</td>
			<td><form:input path="username"/></td>
			<td><form:errors path="username"/></td>
		</tr>

		<tr>
			<td>Password:</td>
			<td><form:password path="password"/></td>
			<td><form:errors path="password"/></td>
		</tr>
		
		<tr>
			<td><form:button>Submit</form:button></td>
		</tr>
	</table>
</form:form>

</body>
</html>