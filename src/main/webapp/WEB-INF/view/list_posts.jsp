<%@ page language="java" contentType="text/html"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<body>
<h1>List of posts soon...</h1>

<br>
<a href="formPost">Create new post</a>
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



<table>
	<tr>
		<th>Title</th>
		<th>Description</th>
		<th>Actions</th>
	</tr>
	
	<c:forEach var="tempPost" items="${posts}">
		<c:url var="deletePost" value="/deletePost">
			<c:param name="postId" value="${tempPost.id}"></c:param>
		</c:url>
		
		<c:url var="updatePost" value="/updatePost">
			<c:param name="postId" value="${tempPost.id}"></c:param>
		</c:url>
	
		<tr>
			<td>${tempPost.title}</td>
			<td>${tempPost.description}</td>
			<td><a href="${deletePost}" 
			onclick="if (!(confirm('Are you sure you want to delete this post?'))) return false">Delete</a>
			 | <a href="${updatePost}">Update</a></td>
		</tr>
	</c:forEach>


</table>

</body>
</html>
