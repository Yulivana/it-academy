<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update user</title>
</head>
<body>
<div>

</div>
	<form action="update" method="post">
		<fieldset style="width: 300px">
			<legend> Update <c:out value='${user.userName}'/> </legend>
			<p style="color:red"><c:out value='${requestScope.error}'/></p>
			<table>
				<tr>
					<td>User ID</td>
					<td><input type="text" name="userName" required="required" value="<c:out value='${user.userName}'/>"/>
					    <input type="hidden" name="id" value="<c:out value='${user.id}'/>"/>
					    <input type="hidden" name="role" value="<c:out value='${user.role}'/>"/></td>
				</tr>
				<tr>
					<td>Old password</td>
					<td><input type="password" name="password" required="required" value="<c:out value='${user.password}'/>"/></td>
				</tr>
				<tr>
                    <td>New password</td>
                    <td><input type="password" name="newPassword" /></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input type="text" name="email" required="required" value="<c:out value='${user.email}'/>"/></td>
                </tr>
				<tr>
					<td><input type="submit" value="Update"/></td>
				</tr>
			</table>
		</fieldset>
	</form>
	<button onclick="location.href='${pageContext.request.contextPath}/welcome'"> welcome </button>
	<c:if test="${role == 'admin'}">
        <a href="${pageContext.request.contextPath}/users">Your users</a>
    </c:if>
    <button onclick="location.href='${pageContext.request.contextPath}/logout'"> logout </button>
</body>
</html>