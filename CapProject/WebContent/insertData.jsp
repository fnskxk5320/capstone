<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action ="${pageContext.request.contextPath}/insertData.do" method= "post">
	<input type = "text" name = "serial" placeholder="sitting" id ="serial" required/><br>
	<input type = "password" name="sitting" placeholder="sitting" id="sitting" required><br>
	<input type="submit" value="submit"><br>
	</form>
</body>
</html>