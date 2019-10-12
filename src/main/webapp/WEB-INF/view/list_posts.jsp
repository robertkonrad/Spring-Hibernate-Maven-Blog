<%@ page language="java" contentType="text/html"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<body>
<h1>List of posts</h1>

<security:authorize access="isAuthenticated()">
	<br>
	<a href="${pageContext.request.contextPath}/post/form">Create new post</a>
	<br>
	
	<br>
	User: <security:authentication property="principal.username"/>
	<br>
	Role: <security:authentication property="principal.authorities"/>
	<br>
	
	<br>
	<form:form action="${pageContext.request.contextPath}/logout" method="POST">
		<input type="submit" value="Logout"/>
	</form:form>
	<br>
</security:authorize>

<security:authorize access="!isAuthenticated()">
	<form:form action="${pageContext.request.contextPath}/login" method="POST">
		<input type="submit" value="Log in"/>
	</form:form>
	<form:form action="${pageContext.request.contextPath}/user/form" method="POST">
		<input type="submit" value="Sign in"/>
	</form:form>
</security:authorize>


<table>
	<tr>
		<th>Title</th>
		<th>Description</th>
		<th>Author</th>
		<security:authorize access="isAuthenticated()">
			<th>Actions</th>
		</security:authorize>
	</tr>
	
	<c:forEach var="tempPost" items="${posts}">
		<spring:url value="/post/delete/${tempPost.id}" var="deletePost"/>
		
		<spring:url value="/post/update/${tempPost.id}" var="updatePost"/>
	
		<tr>
			<td>${tempPost.title}</td>
			<td>${tempPost.description}</td>
			<td>${tempPost.author}</td>
			<security:authorize access="isAuthenticated()">
				<td><a href="${deletePost}" 
				onclick="if (!(confirm('Are you sure you want to delete this post?'))) return false">Delete</a>
				 | <a href="${updatePost}">Update</a></td>
			 </security:authorize>
		</tr>
	</c:forEach>
</table>

<ul>
	<c:forEach var="i" begin="1" end="${pages}">
		<li><a href="${pageContext.request.contextPath}/page/${i}"><c:out value="${i}"></c:out></a></li>
	</c:forEach>
</ul>

</body>
</html>
