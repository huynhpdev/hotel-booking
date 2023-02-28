<%-- 
    Document   : registerPage
    Created on : Sep 28, 2022, 9:17:45 AM
    Author     : MSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery-3.6.1.min.js"></script>
        <script type="text/javascript" src="js/jquery.validate.js"></script>
        <script type="text/javascript" src="js/additional-methods.js"></script>
        <title>REGISTERING ACCOUNT PAGE</title>
    </head>
    <body>
        <script type="text/javascript">
            $(function () {
                $("#registerForm").validate({
                    rules: {
                        txtEmailUp: {
                            required: true,
                            email: true
                        },
                        txtPasswordUp: {
                            required: true,
                            rangelength: [6, 20]
                        },
                        txtReUp: {
                            equalTo: "#txtPass"
                        },
                        txtFullnameUp: {
                            required: true,
                            rangelength: [2, 100]
                        },
                        txtAddressUp: {
                            required: true,
                            rangelength: [10, 100]
                        },
                        txtPhoneUp: {
                            required: true,
                            mobileVN: true
                        }
                    }
                });
            });
        </script>
        <c:if test="${empty sessionScope.FULLNAME}">
            <h1>Welcome to Register Account Page</h1>

            <a href="searchAction">Click here to go to Search Page</a> <br/>
            <a href="loginPage">Click here to login</a> <br/> <br/>

            <c:set var="result" value="${requestScope.REGISTERRESULT}"/>
            <c:if test="${not empty result}">
                <h1>
                    <font color="red">
                    ${result}
                    </font>
                </h1>
            </c:if>
            <form action="registerAction" method="POST" id="registerForm">

                Email <input type="text" name="txtEmailUp" value="${param.txtEmailUp}" /> <br/> <br/>  
                
                Password <input type="password" name="txtPasswordUp" value="" id="txtPass"/> <br/> <br/>
                
                Retype Password <input type="password" name="txtReUp" value="" /> <br/> <br/>
                
                Fullname <input type="text" name="txtFullnameUp" value="${param.txtFullnameUp}" /> <br/> <br/>
                
                Address <input type="text" name="txtAddressUp" value="${param.txtAddressUp}" /> <br/> <br/>   

                Phone <input type="text" name="txtPhoneUp" value="${param.txtPhoneUp}" /> <br/> <br/>
                
                <br/>
                <input type="submit" value="Insert" name="btnAction" />
                <input type="reset" value="Clear" />
            </form>
        </c:if>
        <c:if test="${not empty sessionScope.FULLNAME}">
            <c:redirect url="404Page"/>
        </c:if>
    </body>
</html>
