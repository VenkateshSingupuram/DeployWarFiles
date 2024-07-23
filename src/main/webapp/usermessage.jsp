<%@page import="com.Servlet.UserDetails.UserDetails"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor = cyan>

<%
   UserDetails user = (UserDetails)  request.getAttribute("go");
    out.println("User Details :"+user);
%>

</body>
</html>