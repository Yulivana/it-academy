<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your users</title>
</head>
<body>
    <h2> Your users: </h2>
    <table>
	<c:forEach var="num" items="${list}">
	    <tr>
            <td>${num}</td>
            <td><a href="${pageContext.request.contextPath}/delete?id=${num.id}">delete</a></td>
            <td><a href="${pageContext.request.contextPath}/update?id=${num.id}">update</a></td>
        </tr>
    </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/welcome">welcome</a>
    <button onclick="location.href='${pageContext.request.contextPath}/logout'"> logout </button>
</body>
</html>