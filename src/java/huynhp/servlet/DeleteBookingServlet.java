/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.servlet;

import huynhp.cart.CartObj;
import huynhp.utils.MyAppConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
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
@WebServlet(name = "DeleteBookingServlet", urlPatterns = {"/DeleteBookingServlet"})
public class DeleteBookingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");

        String roomID = request.getParameter("selectedRoomID");
        String url = (String) siteMap.get(MyAppConstants.DeleteBookingFeature.BOOKING_PAGE);

        HttpSession session = request.getSession();
        String msg = "";
        try {
            double totalCart = 0;
            
            List<CartObj> bookRoomList;
            bookRoomList = (List<CartObj>) session.getAttribute("BOOKLIST");
            if (!bookRoomList.isEmpty()) {
                for (CartObj cart : bookRoomList) {
                    // Tìm roomID mà người dùng chọn để xóa sau khi người dùng nhấn xác nhận
                    if (cart.getRoomID() == Integer.parseInt(roomID)) {
                        bookRoomList.remove(cart);
                        break;
                    }
                }
                
                for (CartObj cartObj : bookRoomList) {
                    totalCart += cartObj.getTotal();
                }

                session.setAttribute("TOTALCART", totalCart);
                
                msg = "Deleting room successfully!";
            }
            
            session.setAttribute("BOOKLIST", bookRoomList);
            if (bookRoomList.isEmpty()) {
                session.removeAttribute("REMINDMSG");
                session.removeAttribute("BOOKLIST");
                session.removeAttribute("PERCENTOFDISCOUNT");
                session.removeAttribute("DISCOUNTCODEID");
            }
            request.setAttribute("RESULT", msg);
                  
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
