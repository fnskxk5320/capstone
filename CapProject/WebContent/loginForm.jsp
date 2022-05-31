<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	String id = (String)session.getAttribute("memory");
 %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
 a:link { color: black; text-decoration: none;}
 a:visited { color: black; text-decoration: none;}
 a:hover { color: gray; text-decoration:none;}
  #divPosition {  

    background-color: #ffffff;
    border: 1px solid #5D5D5D;
    position:absolute;
    height:300px;
    width:400px;
    margin:-150px 0px 0px -200px;
    top: 50%;
    left: 50%;
    padding: 5px;
    text-align: center;
   }
   body { padding:0px; margin:0px; }
</style>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>
<div id="divPosition">
<h4>로그인</h4>
	<form action ="${pageContext.request.contextPath}/login.do" method= "post">
	<input type = "text" name = "userId" placeholder="아이디" id ="userId" required/><br>
	<input type = "password" name="password" placeholder="비밀번호" id="password" required><br>
	<input type="submit" value="로그인"><br>
	</form>
</div>
</body>
</html>