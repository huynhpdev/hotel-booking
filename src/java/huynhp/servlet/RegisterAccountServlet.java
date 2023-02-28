/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.servlet;

import huynhp.tbl_User.Tbl_User_DAO;
import huynhp.utils.MyAppConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MSI
 */
@WebServlet(name = "RegisterAccountServlet", urlPatterns = {"/RegisterAccountServlet"})
public class RegisterAccountServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMap.get(MyAppConstants.RegisterAccountFeature.REGISTER_PAGE);

        String userEmail = request.getParameter("txtEmailUp").trim();
        String userPassword = request.getParameter("txtPasswordUp").trim();
        String userFullname = request.getParameter("txtFullnameUp").trim();
        String userAddress = request.getParameter("txtAddressUp").trim();
        String userPhone = request.getParameter("txtPhoneUp").trim();

        Tbl_User_DAO userDAO = null;
        String msg = "";
        try {
            userDAO = new Tbl_User_DAO();
            boolean existedEmail = userDAO.checkExistedEmail(userEmail);
            if (existedEmail) {
                msg = "This account " + userEmail + " is existed in the system already";
                request.setAttribute("REGISTERRESULT", msg);
            } else {
                LocalDate localDate = LocalDate.now();
                userDAO = new Tbl_User_DAO();
                boolean rs = userDAO.registerNewAccount(userEmail, userPassword, 1, 0, userFullname,
                        userAddress, userPhone, localDate.toString());
                if (rs) {
                    msg = "This account " + userEmail + " is created successfully";
                    request.setAttribute("REGISTERRESULT", msg);
                    url = (String) siteMap.get(MyAppConstants.LoginFeature.LOGIN_PAGE);
//                    response.sendRedirect(url);
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
