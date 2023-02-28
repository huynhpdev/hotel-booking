<%-- 
    Document   : confirmBooking
    Created on : Sep 30, 2022, 8:32:12 AM
    Author     : MSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%
            try {
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Cache-Control", "no-store");
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("Expires", 0);
            } catch (Exception e) {
                log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
            }
        %>
        <title>BOOKING ROOM DETAIL</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.CUSTOMER}">
            <c:if test="${not empty sessionScope.CODEVERIFY}">
                <c:if test="${not empty sessionScope.FULLNAME}">
                    <font color="red">
                    Welcome, ${sessionScope.FULLNAME}
                    </font>
                    <form action="logoutAction">
                        <input type="submit" value="Logout" name="btnAction" />
                    </form>  
                </c:if>
                <c:set var="checkoutResult" value="${requestScope.CHECKOUTRESULT}"/>
                <c:if test="${not empty checkoutResult}">
                    <h1 style="color: green">
                        ${checkoutResult}
                    </h1>
                </c:if>
                <h2>
                    Please enter the code to confirm your booking room
                </h2>
                <c:set var="confirmResult" value="${requestScope.CONFIRMRESULT}"/>
                <c:if test="${not empty confirmResult}">
                    <h3 style="color: red">
                        ${confirmResult}
                    </h3>
                </c:if>
                <form action="confirmAction" method="POST">
                    <input type="number" name="txtCode" 
                           value="" pattern="^[0-9]+$" 
                           placeholder="Enter the code"/>
                    <input type="submit" value="Enter" name="btnAction" />
                </form>  <br/>
                <form action="resendVerifyCodeAction" method="GET">
                    <input type="submit" value="Resend Code" name="btnAction" />
                </form> <br/>
            </c:if>
            <c:if test="${empty sessionScope.CODEVERIFY}">
                <c:redirect url="404Page"/>
            </c:if>        
        </c:if>
        <c:if test="${empty sessionScope.CUSTOMER}">
            <c:redirect url="404Page"/>
        </c:if>
    </body>
</html>
