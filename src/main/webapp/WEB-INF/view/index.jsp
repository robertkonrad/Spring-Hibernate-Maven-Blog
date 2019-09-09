<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
<h1>List of posts soon...</h1>

<table>
	<tr>
		<th>Title</th>
	</tr>
	
	<c:forEach var="tempPost" items="${posts}">
		<tr>
			<td>${tempPost.title}</td>
		</tr>
	</c:forEach>


</table>

</body>
</html>
