<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add new user</title>
</head>
<body>
<div>

</div>
	<form action="add" method="post">
		<fieldset style="width: 300px">
			<legend> Login to App </legend>
			<table>
				<tr>
					<td>User Name</td>
					<td><input type="text" name="username" required="required" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" required="required" /></td>
				</tr>
				<tr>
                    <td>Confirm password</td>
                    <td><input type="password" name="confirmPassword" required="required" /></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input type="text" name="email" required="required" /></td>
                </tr>
				<tr>
					<td><input type="submit" value="Add"/></td>
				</tr>
			</table>
		</fieldset>
	</form>
	<button onclick="location.href='${pageContext.request.contextPath}/'"> login page </button>
</body>
</html>