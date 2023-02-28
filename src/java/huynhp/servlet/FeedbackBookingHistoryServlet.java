/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.servlet;

import huynhp.tbl_Booking.Tbl_Booking_DAO;
import huynhp.tbl_QualityRating.Tbl_QualityRating_DAO;
import huynhp.utils.MyAppConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "FeedbackBookingHistoryServlet", urlPatterns = {"/FeedbackBookingHistoryServlet"})
public class FeedbackBookingHistoryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = siteMap.getProperty(MyAppConstants.FeedbackBookingHistoryFeature.VIEW_BOOKING_HISTORY_ACTION);

        HttpSession session = request.getSession();

        int roomID = Integer.parseInt(request.getParameter("selectedRoomID"));
        int bookingID = Integer.parseInt(request.getParameter("selectedBookingID"));
        String userFeedback = request.getParameter("txtFeedback");
        int userRating = Integer.parseInt(request.getParameter("txtRating"));

        String lastSearchHotelName = request.getParameter("lastSearchHotelName");
        String lastSearchBookingDate = request.getParameter("lastSearchBookingDate");

        String msg = "";
        try {

            Tbl_QualityRating_DAO ratingDAO = new Tbl_QualityRating_DAO();
            boolean result = ratingDAO.feedbackBookingRoom(roomID, (Integer) session.getAttribute("USERINFOID"),
                    userFeedback, userRating);
            if (result) {
                
                Tbl_Booking_DAO bookingHistoryDAO = new Tbl_Booking_DAO();
                result = bookingHistoryDAO.setFeedbackStatus(bookingID);

                if (result) {
                    msg = "Feedback successfully!";
                    request.setAttribute("BOOKINGHISTORYRESULT", msg);

                    if ((lastSearchBookingDate != null && lastSearchBookingDate.trim().length() > 0)
                            || (lastSearchHotelName != null && lastSearchHotelName.trim().length() > 0)) {

                        url = "ViewBookingHistoryServlet?"
                                + "&txtHotelName=" + lastSearchHotelName
                                + "&txtBookingDate=" + lastSearchBookingDate;
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
