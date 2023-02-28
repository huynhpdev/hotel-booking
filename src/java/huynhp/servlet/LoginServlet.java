/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.servlet;

import huynhp.tbl_User.Tbl_User_DAO;
import huynhp.tbl_User.Tbl_User_DTO;
import huynhp.utils.MyAppConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final static String ERROR_LOGIN_PAGE = MyAppConstants.LoginFeature.INVALID_PAGE;
    private final static String SEARCH_PAGE = MyAppConstants.SearchHotelFeature.HOTEL_PAGE;
    private final static String SEARCH_ACTION = MyAppConstants.SearchHotelFeature.SEARCH_HOTEL_ACTION;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();    
        
        String userEmail = request.getParameter("txtEmail");

        String userPassword = request.getParameter("txtPassword");

        String url = ERROR_LOGIN_PAGE;
        try {
            
            Tbl_User_DAO userDAO = new Tbl_User_DAO();
            Tbl_User_DTO userDTO = null;
            userDTO = userDAO.checkLogin(userEmail, userPassword);

            String userName = userDTO.getName();

            // Tạo mới session
            HttpSession session = request.getSession(true);
            session.setAttribute("FULLNAME", userName);

            String roleName = "";
            int roleID = userDTO.getRoleID();
            if (roleID == MyAppConstants.ROLE_ADMIN) {
                roleName = "Admin";
                session.setAttribute("ADMIN", roleName);
                url = SEARCH_PAGE;
            } else {
                roleName = "Customer";
                session.setAttribute("CUSTOMER", roleName);
                session.setAttribute("USERINFOID", userDTO.getUserInfoID());
                session.setAttribute("USEREMAIL", userDTO.getEmail());
                url = SEARCH_ACTION;
            }

        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
        } finally {
            response.sendRedirect(url);
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
