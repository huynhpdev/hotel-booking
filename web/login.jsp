<%-- 
    Document   : login
    Created on : Sep 28, 2022, 5:03:34 PM
    Author     : MSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOGIN PAGE</title>
    </head>
    <body>
        <c:if test="${empty sessionScope.FULLNAME}">
        <h1>Login Page</h1>
        <c:set var="result" value="${requestScope.REGISTERRESULT}"/>
        <c:if test="${not empty result}">
            <h1>
                <font color="red">
                ${result}
                </font>
            </h1>
        </c:if>
        <br/>
        <form action="loginAction" method="POST">
            Email <input type="text" name="txtEmail" value="" /> <br/>
            Password <input type="password" name="txtPassword" value="" /> <br/> <br/>
            <input type="submit" value="Login" name="btnAction" />
            <input type="reset" value="Reset" />
        </form>    
        <br/>
        <a href="registerPage">
            <font color="red">
            No account? Click here
            </font>
        </a> 
        <span style="padding-left:50px"/>   
        <a href="searchAction">Click here to go to Search Page</a>
        </c:if>
        <c:if test="${not empty sessionScope.FULLNAME}">
            <c:redirect url="404Page"/>
        </c:if>
    </body>
</html>
