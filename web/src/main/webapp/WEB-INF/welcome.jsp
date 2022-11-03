<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>
	<h3>Login successful!!!</h3>
	<h4>
		Hello, <c:out value="${name}"/> </h4>
        <a href="${pageContext.request.contextPath}/update">update</a>
<c:if test="${role == 'admin'}">
    <a href="${pageContext.request.contextPath}/users">Your users</a>
</c:if>
    <button onclick="location.href='${pageContext.request.contextPath}/logout'"> logout </button>
</body>
</html>