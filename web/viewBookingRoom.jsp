<%-- 
    Document   : viewBookingRoom
    Created on : Sep 29, 2022, 8:10:11 AM
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
        <title>VIEW BOOKING ROOM</title>
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
            <h1>Your Booking Room includes</h1>
            <a href="searchAction">Click here to go to Search Page</a> <br/> <br/>
            <c:set var="msg" value="${requestScope.RESULT}"/>
            <c:if test="${not empty msg}">
                <h2>
                    <font color="red">
                    ${msg}
                    </font>
                </h2>
            </c:if>
            <div class="bookingPage">
                <c:if test="${not empty sessionScope.BOOKLIST}">
                    <c:if test="${not empty sessionScope.REMINDMSG}">
                        <p>
                            <font color="blue">
                            ${sessionScope.REMINDMSG}
                            </font>
                        </p>
                    </c:if>
                    <form action="checkoutBookingPage" method="GET"> 
                        <input type="submit" value="Checkout" name="btnAction" />
                    </form><br/>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Hotel Name</th>
                                <th>Room Type</th>
                                <th>Amount</th>
                                <th>Original Price</th>
                                <th>Discount Price</th>
                                <th>Total Price</th>
                                <th>Delete</th>
                                <th>Update</th>
                            </tr>
                        </thead>                                       
                        <tbody>             
                            <c:forEach var="bookRoom" items="${sessionScope.BOOKLIST}">
                            <form action="updateBookingAction" method="GET">   
                                <tr>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${bookRoom.hotelName}
                                        <input type="hidden" name="roomID" value="${bookRoom.roomID}" />
                                        <c:set var="txtRoomID" value="${bookRoom.roomID}"/>
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${bookRoom.roomType}
                                    </td>
                                    <td>
                                        <input style="text-align: center; vertical-align: middle;"
                                               type="number" name="txtAmount" 
                                               min="1" max="100" maxlength="3"
                                               title="Value is greater than 1" value="${bookRoom.amount}" 
                                               pattern="^[0-9]+$" placeholder="Enter the amount of room" 
                                               oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" 
                                               required/>
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
                                    <td>
                                        <input id="delete" type="button" value="Delete" 
                                               name="" onclick="popupDeleteWindow('deleteWindow', '${txtRoomID}')"/>
                                    </td>
                                    <td>
                                        <input type="submit" value="Update" name="btnAction" />
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                        </tbody>
                    </table>
                    <form action="deleteBookingAction" method="GET">             
                        <div id="warningDeleteMessage">
                            <div id="warningDeleteMessage_content">
                                <div id="deleteWindow">
                                    <h2>Click confirm to delete selected item.</h2> <br/>
                                    <div>                
                                        <input id="roomID" type="hidden" name="selectedRoomID" value="" />
                                        <input class="cancellation" type="button" name="" value="Cancel" onclick="closeDeleteWindow('deleteWindow')"/>
                                        <input class="delete" type="submit" name="btnAction" value="Delete Room"/>
                                    </div>
                                </div>
                            </div>
                        </div> 
                    </form> <br/> <br/>                                   
                </c:if>
                <c:if test="${empty sessionScope.BOOKLIST}">
                    <a href="searchAction">
                        <h2>
                            <font color="red">
                            Booking Room List is empty. Click here to go back Search Page
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
