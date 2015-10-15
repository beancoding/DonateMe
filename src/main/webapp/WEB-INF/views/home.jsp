<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>

	<head>
		<title>Home</title>
	</head>
	
	<body>
	
		<h1>Welcome</h1>
		
		<c:forEach items="${names}" var="productName">
			<p>${productName}</p>
		</c:forEach>
		
	</body>
	
</html>
