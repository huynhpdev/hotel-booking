/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.servlet;

import huynhp.tbl_Booking.Tbl_Booking_DAO;
import huynhp.tbl_Booking.Tbl_Booking_DTO;
import huynhp.utils.DBHelper;
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
@WebServlet(name = "ViewBookingHistoryServlet", urlPatterns = {"/ViewBookingHistoryServlet"})
public class ViewBookingHistoryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMap.get(MyAppConstants.ViewBookingHistoryFeature.BOOKING_HISTORY_PAGE);

        String txtHotelName = request.getParameter("txtHotelName");
        String txtBookingDate = request.getParameter("txtBookingDate");
        HttpSession session = request.getSession();
        Tbl_Booking_DAO bookingHistoryDAO = null;
        try {
            int userInfoID = (Integer) session.getAttribute("USERINFOID");                     
            if (txtHotelName == null && txtBookingDate == null) {
                bookingHistoryDAO = new Tbl_Booking_DAO();
                bookingHistoryDAO.getAllBookingHistoryByUser(userInfoID);

                List<Tbl_Booking_DTO> result = bookingHistoryDAO.getBookingList();
                request.setAttribute("BOOKINGHISTORY", result);
            }

            if ((txtHotelName != null && txtHotelName.trim().length() > 0)
                    || (txtBookingDate != null && txtBookingDate.trim().length() > 0)) {
                List<Tbl_Booking_DTO> result = null;
                if (txtHotelName.trim().length() > 0 && txtBookingDate.trim().length() == 0) {
                    bookingHistoryDAO = new Tbl_Booking_DAO();
                    bookingHistoryDAO.getAllBookingHistoryByHotelName(userInfoID, txtHotelName);
                    result = bookingHistoryDAO.getBookingList();
                }

                if (txtBookingDate.trim().length() > 0 && txtHotelName.trim().length() == 0) {
                    bookingHistoryDAO = new Tbl_Booking_DAO();
                    bookingHistoryDAO.getAllBookingHistoryByBookingDate(userInfoID, DBHelper.convertDateTimeFromUserInput(txtBookingDate));
                    result = bookingHistoryDAO.getBookingList();
                }

                if (txtHotelName.trim().length() > 0 && txtBookingDate.trim().length() > 0) {
                    bookingHistoryDAO = new Tbl_Booking_DAO();
                    bookingHistoryDAO.getAllBookingHistoryByBookingDateAndHotelName(userInfoID, txtHotelName, DBHelper.convertDateTimeFromUserInput(txtBookingDate));
                    result = bookingHistoryDAO.getBookingList();
                }
                request.setAttribute("BOOKINGHISTORY", result);
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
