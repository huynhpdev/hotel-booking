/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.servlet;

import huynhp.mailValidCode.MailValidCode;
import huynhp.tbl_Booking.Tbl_Booking_DAO;
import huynhp.utils.MyAppConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "ConfirmBookingServlet", urlPatterns = {"/ConfirmBookingServlet"})
public class ConfirmBookingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");

        String url = (String) siteMap.get(MyAppConstants.SearchHotelFeature.SEARCH_HOTEL_ACTION);

        String txtCode = request.getParameter("txtCode");
        String msg = "";

        HttpSession session = request.getSession();
        try {

            int userInfoID = (Integer) session.getAttribute("USERINFOID");
            
            String verifyCode = (String) session.getAttribute("CODEVERIFY");
            if (txtCode.equals(verifyCode)) {
                
                List<Integer> userBooking;
                userBooking = (List<Integer>) session.getAttribute("USERBOOKING");

                if (userBooking != null) {
                    boolean result = false;
                    for (Integer roomID : userBooking) {
                        Tbl_Booking_DAO bookingDAO = new Tbl_Booking_DAO();
                        int getBookingID = bookingDAO.getBookingID(userInfoID, roomID);
                        result = bookingDAO.confirmBooking(getBookingID);
                    }

                    if (result) {
                        session.removeAttribute("CODEVERIFY");
                        session.removeAttribute("USERBOOKING");
                        msg = "Confirm Booking Successfully!";
                        request.setAttribute("CONFIRMRESULT", msg);
                    }
                }
            } else {
                msg = "Verify failed, the code is not correct. Please try again";
                request.setAttribute("CONFIRMRESULT", msg);
                url = (String) siteMap.get(MyAppConstants.ConfirmBookingFeature.CONFIRM_PAGE);
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
