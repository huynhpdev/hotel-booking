/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.servlet;

import huynhp.tbl_Room.Tbl_Room_DAO;
import huynhp.tbl_Room.Tbl_Room_DTO;
import huynhp.utils.DBHelper;
import huynhp.utils.MyAppConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
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
@WebServlet(name = "HotelSearchServlet", urlPatterns = {"/HotelSearchServlet"})
public class HotelSearchServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMap.get(MyAppConstants.SearchHotelFeature.HOTEL_PAGE);

        Tbl_Room_DAO roomDAO = null;
        String txtHotelNameSearch = request.getParameter("txtHotelName");
        String txtCheckinSearch = request.getParameter("txtCheckin");
        String txtCheckoutSearch = request.getParameter("txtCheckout");
        String txtRoomSearch = request.getParameter("txtRoom");

        int roomSearch = 0;

        HttpSession session = request.getSession();
        String roleName = (String) session.getAttribute("ADMIN");
        try {
            if (txtHotelNameSearch == null
                    && txtCheckinSearch == null
                    && txtCheckoutSearch == null
                    && txtRoomSearch == null) {
                if (roleName == null) {
                    LocalDate localDate = LocalDate.now();
 
                    roomDAO = new Tbl_Room_DAO();
                    roomDAO.getAllAvailableRoomInToday(localDate.toString());
                    List<Tbl_Room_DTO> result = roomDAO.getAvailableRoomList();

                    request.setAttribute("HOTELRESULT", result);
                }
            }

            if ((txtHotelNameSearch != null && txtHotelNameSearch.trim().length() > 0)
                    && (txtCheckinSearch != null && txtCheckinSearch.trim().length() > 0)
                    && (txtCheckoutSearch != null && txtCheckoutSearch.trim().length() > 0)) {
                
                if (txtRoomSearch == null || txtRoomSearch.trim().length() == 0) {
                    roomSearch = 1;
                } else {
                    roomSearch = Integer.parseInt(txtRoomSearch);
                }

                String checkInDate = DBHelper.convertDateTimeFromUserInput(txtCheckinSearch);
                String checkOutDate = DBHelper.convertDateTimeFromUserInput(txtCheckoutSearch);

                roomDAO = new Tbl_Room_DAO();

                roomDAO.getAllAvailableRoomByCICO(txtHotelNameSearch, roomSearch, checkInDate, checkOutDate);
                List<Tbl_Room_DTO> result = roomDAO.getAvailableRoomList();

                request.removeAttribute("HOTELRESULT");
                request.setAttribute("HOTELRESULT", result);
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
