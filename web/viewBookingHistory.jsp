<%-- 
    Document   : viewBookingHistory
    Created on : Oct 1, 2022, 1:37:27 PM
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
        <title>BOOKING HISTORY PAGE</title>
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
            <h1>Your Booking History includes</h1>
            <a href="searchAction">Click here to go to Search Page</a> <br/> <br/>
            <c:set var="deletedMsg" value="${requestScope.BOOKINGHISTORYRESULT}"/>
            <c:if test="${not empty deletedMsg}">
                <h2 style="color: red">
                    ${deletedMsg}
                </h2>
            </c:if> 
            <form action="viewBookingHistoryAction" method="GET">
                Search <input type="text" name="txtHotelName" placeholder="Hotel Name"
                              value="${param.txtHotelName}" />

                <input type="text" name="txtBookingDate" value="${param.txtBookingDate}" placeholder="Booking Date"
                       title="DD-MM-YYYY" pattern="^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$"/>
                
                <input type="submit" value="Search" name="btnAction"/>
            </form>
            <br/>
            <c:set var="searchHotelName" value="${param.txtHotelName}"/>
            <c:set var="searchBookingDate" value="${param.txtBookingDate}"/>
            <c:set var="result" value="${requestScope.BOOKINGHISTORY}"/>
            <div class="bookingHistoryPage">
                <c:if test="${not empty result}">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Hotel Name</th>
                                <th>Room Type</th>
                                <th>Amount</th>
                                <th>Available Date To Checkin</th>
                                <th>Available Date To Checkout</th>
                                <th>Price</th>
                                <th>Percent Of Discount</th>
                                <th>Total</th>
                                <th>Booking Date</th>
                                <th>Confirm Booking</th>
                                <th>Action</th>
                                <th>Feedback</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="historyDTO" items="${result}">
                            <form action="deleteBookingHistoryAction">
                                <tr>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${historyDTO.hotelName}
                                        <input type="hidden" name="txtBookingID" value="${historyDTO.bookingID}" />
                                        <input type="hidden" name="txtRoomID" value="${historyDTO.roomID}" />
                                        <c:set var="bookingIDFeedback" value="${historyDTO.bookingID}"/>
                                        <c:set var="roomIDFeedback" value="${historyDTO.roomID}"/>
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${historyDTO.roomType}
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${historyDTO.amount}
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${historyDTO.checkInDate}
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${historyDTO.checkOutDate}
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${historyDTO.price}
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        <c:if test="${historyDTO.percentOfDiscount == 0}">
                                            Không có áp dụng mã giảm giá
                                        </c:if>
                                        <c:if test="${historyDTO.percentOfDiscount > 0}">
                                            ${historyDTO.percentOfDiscount}
                                        </c:if>
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${historyDTO.total}
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        ${historyDTO.bookDate}
                                    </td>
                                    <td style="text-align: center; vertical-align: middle;">
                                        <input type="checkbox" disabled="disabled"
                                               <c:if test="${historyDTO.confirmStatus == 0}">
                                                   checked = "checked" 
                                               </c:if>
                                               />
                                    </td>                             
                                    <td>
                                        <input type="hidden" name="lastSearchHotelName" value="${searchHotelName}" />
                                        <input type="hidden" name="lastSearchBookingDate" value="${searchBookingDate}"/> 
                                        <input type="submit" value="Delete" name="btnAction" />
                                    </td>
                                    <td>
                                        <c:if test="${historyDTO.allowFeedback}">
                                            <input id="feedback" type="button" value="Feedback" name="" 
                                                   onclick="popupFeedbackWindow('feedbackWindow', '${bookingIDFeedback}', '${roomIDFeedback}')"/>
                                        </c:if>
                                        <c:if test="${!historyDTO.allowFeedback}">
                                            <input type="button" value="Feedback" name="" disabled="disabled"/>
                                        </c:if>
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                        </tbody>
                    </table>
                    <form action="feedbackBookingHistoryAction" method="GET">             
                        <div id="warningFeedbackMessage">
                            <div id="warningFeedbackMessage_content">
                                <div id="feedbackWindow">
                                    <h2>Fill out your feedback and your rating: </h2>
                                    Your feedback <input type="text" name="txtFeedback" value="" required/> <br/> <br/>
                                    Your rating <input type="number" 
                                           pattern="^[0-9]+$" 
                                           value=""
                                           name="txtRating"
                                           min="1"
                                           max="10"
                                           maxlength="2"
                                           title="Only number and the range is 1-10"
                                           required/>

                                    <div>
                                    <div>        
                                        <input id="selectBookingID" type="hidden" name="selectedBookingID" value="" />
                                        <input id="selectRoomID" type="hidden" name="selectedRoomID" value="" />
                                        <input type="hidden" name="lastSearchHotelName" value="${searchHotelName}" />
                                        <input type="hidden" name="lastSearchBookingDate" value="${searchBookingDate}"/> 
                                        <input class="cancellation" type="button" name="" value="Cancel" onclick="closeFeedbackWindow('feedbackWindow')"/>
                                        <input class="feedback" type="submit" name="btnAction" value="Feedback"/>
                                    </div>
                                </div>
                            </div>
                        </div> 
                    </form> <br/> <br/> 
                </c:if>
                <c:if test="${empty result}">
                    <h2>
                        <font color="red">
                        No Result Found
                        </font>
                    </h2>
                </c:if>
            </div>
        </c:if>
        <c:if test="${empty sessionScope.CUSTOMER}">
            <c:redirect url="404Page"/>
        </c:if>
    </body>
</html>
