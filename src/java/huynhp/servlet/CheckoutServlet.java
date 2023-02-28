/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.servlet;

import huynhp.cart.CartObj;
import huynhp.mailValidCode.MailValidCode;
import huynhp.tbl_Booking.Tbl_Booking_DAO;
import huynhp.tbl_Room.Tbl_Room_DAO;
import huynhp.utils.MyAppConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MSI
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = siteMap.getProperty(MyAppConstants.CheckoutBookingFeature.CHECKINGOUT_PAGE);

        HttpSession session = request.getSession();

        String msg = "";
        Tbl_Room_DAO roomDAO = null;
        try {
            int userInfoID = (Integer) session.getAttribute("USERINFOID");
            String userEmail = (String) session.getAttribute("USEREMAIL");
            LocalDate localDate = LocalDate.now();

            List<CartObj> bookRoomList;
            bookRoomList = (List<CartObj>) session.getAttribute("BOOKLIST");
            if (!bookRoomList.isEmpty()) {
                boolean result = false;
                for (CartObj cart : bookRoomList) {
                    roomDAO = new Tbl_Room_DAO();
                    result = roomDAO.checkAvailableRoom(cart.getRoomID(), cart.getAmount());
                    if (!result) {
                        msg = "This roomType: " + cart.getRoomType() + " of the hotel "
                                + cart.getHotelName() + " does not have enough available room to book";
                        request.setAttribute("CHECKOUTRESULT", msg);
                        bookRoomList.remove(cart);
                        break;
                    }
                }
                // Khi tất cả phòng đều còn đủ để đặt
                if (result) {
                    List<Integer> userBooking = new ArrayList<>();
                    boolean rs = false;
                    boolean isBooked = false;
                    for (CartObj cart : bookRoomList) {
                        userBooking.add(cart.getRoomID());

                        int discountCodeID = 0;
                        discountCodeID = (Integer) session.getAttribute("DISCOUNTCODEID");

                        Tbl_Booking_DAO bookingDAO = new Tbl_Booking_DAO();
                        if (discountCodeID > 0) {
                            isBooked = bookingDAO.bookRoom(userInfoID, cart.getRoomID(), discountCodeID, cart.getAmount(),
                                    localDate.toString(), cart.getTotal(), 0, 1, 1);
                        } else {
                            isBooked = bookingDAO.bookRoom(userInfoID, cart.getRoomID(), 0, cart.getAmount(),
                                    localDate.toString(), cart.getTotal(), 0, 1, 1);
                        }

                        if (isBooked) {
                            roomDAO = new Tbl_Room_DAO();
                            rs = roomDAO.getBookingRoomAndDecreaseAvailableRoom(cart.getRoomID(), cart.getAmount());
                        }
                    }
                    if (rs) {
                        msg = "Booking room successfully";
                        request.setAttribute("CHECKOUTRESULT", msg);

                        session.removeAttribute("REMINDMSG");
                        session.removeAttribute("PERCENTOFDISCOUNT");
                        session.removeAttribute("DISCOUNTCODEID");
                        session.removeAttribute("BOOKLIST");
                        session.removeAttribute("TOTALCART");

                        session.setAttribute("USERBOOKING", userBooking);

                        MailValidCode validCode = new MailValidCode();
                        String randomCode = validCode.getRandom();
                        session.setAttribute("CODEVERIFY", randomCode);
                        boolean isSent = validCode.sendEmail(randomCode, userEmail);
                        if (isSent) {
                            url = siteMap.getProperty(MyAppConstants.ConfirmBookingFeature.CONFIRM_PAGE);
                        }
                    }
                } else {
                    if (bookRoomList.isEmpty()) {
                        System.out.println("BookList is empty");

                        session.removeAttribute("REMINDMSG");
                        session.removeAttribute("PERCENTOFDISCOUNT");
                        session.removeAttribute("DISCOUNTCODEID");
                        session.removeAttribute("BOOKLIST");
                    } else {             
                        session.setAttribute("BOOKLIST", bookRoomList);
                    }
                }

            }
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
