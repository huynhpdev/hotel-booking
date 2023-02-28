<%-- 
    Document   : 404.jsp
    Created on : Oct 4, 2022, 7:43:38 AM
    Author     : MSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>404 Page</title>
    </head>
    <body>
        <h1 style="font-size: 50px; color: red"> 

            Something is wrong

        </h1>
        <a href="searchAction">Click here to go to Search Page</a> <br/>
 
        <c:if test="${empty sessionScope.FULLNAME}">
            <a href="loginPage">Click here to login</a>
        </c:if>
    </body>
</html>
