<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
        Enumeration headerEnum = request.getHeaderNames();
        while (headerEnum.hasMoreElements()) {
            String headerName = (String) headerEnum.nextElement();
            String headerValue = request.getHeader(headerName);
    %>
    <%=headerName%>
    <%=headerValue%><br />
   <%
       }
   %>
   <%=request.getContextPath() %>
	<form action ="${pageContext.request.contextPath}/insertData.do" method= "post">
		<input type = "text" name = "serial" placeholder="시리얼" id ="serial" required/><br>
		<input type = "text" name="sitting" placeholder="비밀번호" id="sitting" required><br>
		<input type="submit" value="전송"><br>
	</form>
</body>
</html>