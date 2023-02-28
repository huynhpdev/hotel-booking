<%-- 
    Document   : bookHotel
    Created on : Sep 24, 2022, 10:41:36 PM
    Author     : MSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery-3.6.1.min.js"></script>
        <script type="text/javascript" src="js/jquery.validate.js"></script>
        <script type="text/javascript" src="js/additional-methods.js"></script>
        <title>HOTEL SEARCHING PAGE</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.FULLNAME}">
            <font color="red">
            Welcome, ${sessionScope.FULLNAME}
            </font>
            <form action="logoutAction">
                <input type="submit" value="Logout" name="btnAction" />
            </form>  
        </c:if>
        <c:if test="${empty sessionScope.FULLNAME}">
            <a href="loginPage">Click here to login</a> <br/>
            <a href="registerPage">Click here to register new account</a> <br/>
        </c:if>    

        <br/>
        <c:if test="${not empty sessionScope.ADMIN}">
            <h1>
                Welcome back, Admin
            </h1>
        </c:if>
        <c:if test="${empty sessionScope.ADMIN}">
            <h1>Welcome to Hotel Searching Page</h1>
            <br/>
            <c:set var="confirmResult" value="${requestScope.CONFIRMRESULT}"/>
            <c:if test="${not empty confirmResult}">
                <h2 style="color: green">
                    ${confirmResult}
                </h2>
            </c:if> <br/>
            <form action="searchAction" method="GET">
                Search <input type="text" name="txtHotelName" placeholder="Hotel Name"
                              value="${param.txtHotelName}" />

                <input type="text" name="txtCheckin" value="${param.txtCheckin}" placeholder="Checkin Date"
                       title="DD-MM-YYYY" pattern="^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$" />

                <input type="text" name="txtCheckout" value="${param.txtCheckout}" placeholder="Checkout Date"
                       title="DD-MM-YYYY" pattern="^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$" />

                <input type="number" name="txtRoom" placeholder="Quantity Room" min="1" max="100" maxlength="3"
                       value="${param.txtRoom}" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                <input type="submit" value="Search" name="btnAction" />
            </form>
            <br/>
            <c:set var="hotelNameSearch" value="${param.txtHotelName}"/>
            <c:set var="checkInDateSearch" value="${param.txtCheckin}"/>
            <c:set var="checkOutDateSearch" value="${param.txtCheckout}"/>
            <c:set var="roomSearch" value="${param.txtRoom}"/>
            <c:set var="result" value="${requestScope.HOTELRESULT}"/>
            <c:if test="${not empty result}">
                <c:set var="addRoomResult" value="${requestScope.ADDROOMRESULT}"/>
                <c:if test="${not empty addRoomResult}">
                    <h2 style="color: green">
                        ${addRoomResult}
                    </h2>
                </c:if>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Hotel Name</th>
                            <th>Room Type</th>
                            <th>Available Room</th>
                            <th>Price</th>
                            <th>Available Date To Checkin</th>
                            <th>Available Date To Checkout</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="roomDTO" items="${result}">
                        <form action="addAction"> 
                            <tr>
                                <td style="text-align: center; vertical-align: middle;">
                                    ${roomDTO.hotelName}

                                </td>
                                <td style="text-align: center; vertical-align: middle;">
                                    ${roomDTO.roomType}
                                </td>
                                <td style="text-align: center; vertical-align: middle;">
                                    ${roomDTO.availableRoom}
                                </td>
                                <td style="text-align: center; vertical-align: middle;">
                                    ${roomDTO.price}
                                </td>
                                <td style="text-align: center; vertical-align: middle;">
                                    ${roomDTO.checkInDate}
                                </td>
                                <td style="text-align: center; vertical-align: middle;">
                                    ${roomDTO.checkOutDate}
                                </td>
                                <td>
                                    <input type="hidden" name="lastHotelNameSearch" value="${hotelNameSearch}" />
                                    <input type="hidden" name="checkInDateSearch" value="${checkInDateSearch}" />
                                    <input type="hidden" name="checkOutDateSearch" value="${checkOutDateSearch}" />
                                    <input type="hidden" name="roomSearch" value="${roomSearch}" />

                                    <input type="hidden" name="txtRoomIDDTO" value="${roomDTO.roomID}" />
                                    <input type="hidden" name="txtHotelNameDTO" value="${roomDTO.hotelName}" />
                                    <input type="hidden" name="txtRoomTypeDTO" value="${roomDTO.roomType}" />
                                    <input type="hidden" name="txtPriceDTO" value="${roomDTO.price}" />
                                    <input type="submit" value="Book Room" name="btnAction" />
                                </td>
                            </tr> 
                        </form>
                    </c:forEach>
                </tbody>
            </table>
            <br/> <br/>
        </c:if>
        <c:if test="${empty result}">
            <font color="red">
            No Result Found
            </font> <br/> <br/>
        </c:if>
        <c:if test="${not empty sessionScope.CUSTOMER}">
            <form action="viewBookingPage" method="GET">
                <input type="submit" value="View your booking" name="btnAction" />
            </form><br/>
        </c:if>
        <c:if test="${not empty sessionScope.CUSTOMER}">
            <form action="viewBookingHistoryAction" method="GET">
                <input type="submit" value="View your booking history" name="btnAction" />
            </form><br/>
        </c:if>
    </c:if>       
</body>
</html>
