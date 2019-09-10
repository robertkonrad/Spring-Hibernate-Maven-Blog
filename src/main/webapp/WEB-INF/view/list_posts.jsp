<%@ page language="java" contentType="text/html"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
<h1>List of posts soon...</h1>

<table>
	<tr>
		<th>Title</th>
		<th>Description</th>
		<th>Actions</th>
	</tr>
	
	<c:forEach var="tempPost" items="${posts}">
		<c:url var="deletePost" value="/delete_post">
			<c:param name="postId" value="${tempPost.id}"></c:param>
		</c:url>
	
		<tr>
			<td>${tempPost.title}</td>
			<td>${tempPost.description}</td>
			<td><a href="${deletePost}" 
			onclick="if (!(confirm('Are you sure you want to delete this post?'))) return false">Delete</a></td>
		</tr>
	</c:forEach>


</table>

</body>
</html>
