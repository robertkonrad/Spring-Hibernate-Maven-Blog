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
<h1>Post form</h1>

<form:form action="savePost" method="post" modelAttribute="post">
    <form:hidden path="id" />
	<table>
		<tr>
			<td>Title:</td>
			<td><form:input path="title"/></td>
			<td><form:errors path="title"/></td>
		</tr>

		<tr>
			<td>Description:</td>
			<td><form:input path="description"/></td>
			<td><form:errors path="description"/></td>
		</tr>
		
		<tr>
			<td><form:button>Submit</form:button></td>
		</tr>
	</table>
</form:form>

</body>
</html>