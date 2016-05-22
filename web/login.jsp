<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>



<html>
	<head>
		<title>Login</title>
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<meta HTTP-EQUIV="Expires" CONTENT="-1">
	</head>
	<body>
		<form accept-charset="ISO-8859-1">
			<table>
				<tr>
					<td>Username</td>
					<td><input type="text" name=username">				
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password"></td>				
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit"  value="Login"></td>				
				</tr>	
				</table>	
				<a href=register.jsp> Register </a>					
		</form>
	<body>	
</html>