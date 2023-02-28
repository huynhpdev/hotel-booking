<%-- 
    Document   : checkoutBookingRoom
    Created on : Sep 30, 2022, 9:54:33 AM
    Author     : MSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="css/common.css" rel="stylesheet" type="text/css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/common.js"></script>
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
        <title>CHECKINGOUT DETAIL</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.CUSTOMER}">
            <c:if test="${not empty sessionScope.FULLNAME}">
                <font color="red">
                Welcome, ${sessionScope.FULLNAME}
                </font>
                <form action="logoutAction">
                    <input type="submit" value="Logout" name="btnAction" />
                </form>  
            </c:if>
            <h1>Your checkout booking detail</h1>
            <a href="searchAction">Click here to go to Search Page</a> <br/> <br/>
            <a href="viewBookingPage">Click here to go to View Booking Page</a> <br/> <br/>
            <c:set var="checkoutResult" value="${requestScope.CHECKOUTRESULT}"/>
            <c:if test="${not empty checkoutResult}">
                <h2 style="color: brown">
                    ${checkoutResult}
                </h2>
            </c:if> <br/>
            <div class="checkingoutPage">
                <c:if test="${not empty sessionScope.BOOKLIST}">
                    <c:set var="successfulDiscount" value="${requestScope.SUCCESSDISCOUNT}"/>
                    <c:if test="${not empty successfulDiscount}">
                        <h2>
                            <font color="green">
                            ${successfulDiscount}
                            </font>
                        </h2>
                    </c:if>
                    <c:set var="failedDiscount" value="${requestScope.FAILEDDISCOUNT}"/>
                    <c:if test="${not empty failedDiscount}">
                        <h2>
                            <font color="red">
                            ${failedDiscount}
                            </font>
                        </h2>
                    </c:if>

                    <c:if test="${not empty sessionScope.REMINDMSG}">
                        <p>
                            <font color="blue">
                            ${sessionScope.REMINDMSG}
                            </font>
                        </p>
                    </c:if>
                    <form action="discountAction" method="POST">
                        <input type="text" name="txtVoucher" 
                               placeholder="Enter the voucher" value="${param.txtVoucher}"/>
                        <input type="submit" value="Use" name="btnAction" />
                    </form> <br/>
                    <input id="confirm" type="button" value="Confirm Booking" 
                           name="" onclick="popupConfirmWindow('confirmWindow')"/>
                    <form action="checkoutBookingAction">             
                        <div id="warningMessage">
                            <div id="warningMessage_content">
                                <div id="confirmWindow">
                                    <h2>Click confirm to checkout your booking</h2> <br/>
                                    <div>                
                                        <input class="cancellation" type="button" name="" value="Cancel" onclick="closeConfirmWindow('confirmWindow')"/>
                                        <input class="confirm" type="submit" name="btnAction" value="Confirm"/>
                                    </div>
                                </div>
                            </div>
                        </div> 
                    </form> <br/>

                    Total payment <input type="text" name="txtTotalCart" 
                                         style="text-align: center; vertical-align: middle;"
                                         value="${sessionScope.TOTALCART}" disabled="disabled"/> 
                    <br/> <br/>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Hotel Name</th>
                                <th>Room Type</th>
                                <th>Amount</th>
                                <th>Original Price</th>
                                <th>Discount Price</th>
                                <th>Total Price</th>
                            </tr>
                        </thead>                                       
                        <tbody>             
                            <c:forEach var="bookRoom" items="${sessionScope.BOOKLIST}">
                                <tr>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${bookRoom.hotelName}
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${bookRoom.roomType}
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${bookRoom.amount}
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${bookRoom.originalPrice}
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${bookRoom.discountPrice}
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${bookRoom.total}
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty sessionScope.BOOKLIST}">
                    <a href="searchAction">
                        <h2>
                            <font color="red">
                            Checkingout List is empty. Click here to go back Search Page
                            </font>
                        </h2>
                    </a>
                </c:if>
            </div>
        </c:if>
        <c:if test="${empty sessionScope.CUSTOMER}">
            <c:redirect url="404Page"/>
        </c:if>
    </body>
</html>
