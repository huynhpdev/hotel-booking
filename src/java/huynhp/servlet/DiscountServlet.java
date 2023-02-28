/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.servlet;

import huynhp.cart.CartObj;
import huynhp.tbl_DiscountCode.Tbl_DiscountCode_DAO;
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
@WebServlet(name = "DiscountServlet", urlPatterns = {"/DiscountServlet"})
public class DiscountServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");

        String discountCode = request.getParameter("txtVoucher").toUpperCase();
        String url = (String) siteMap.get(MyAppConstants.CheckoutBookingFeature.CHECKINGOUT_PAGE);

        String msg = "";

        HttpSession session = request.getSession();
        try {
            if (discountCode != null && discountCode.trim().length() > 0) {
                Tbl_DiscountCode_DAO discountDAO = new Tbl_DiscountCode_DAO();
                // Kiểm tra xem voucher có tồn tại hay không
                boolean isExisted = discountDAO.checkExistedDiscountCode(discountCode);
                if (isExisted) {
                    LocalDate localDate = LocalDate.now();
                    boolean isUsable = discountDAO.checkExpiryDateOfDiscountCode(discountCode, localDate.toString());
                    // Kiểm tra xem voucher còn hạn sử dụng không
                    if (isUsable) {
                        int percentOfDiscount = 0;
                        percentOfDiscount = discountDAO.takePercentOfDiscountCode(discountCode);
                        // Kiểm tra xem voucher giảm hơn 0% hay không
                        if (percentOfDiscount > 0) {
                            session.setAttribute("PERCENTOFDISCOUNT", percentOfDiscount);

                            int discountID = 0;
                            discountID = discountDAO.takeDiscountcodeID(discountCode);
                            session.setAttribute("DISCOUNTCODEID", discountID);

                            List<CartObj> bookRoomList;
                            bookRoomList = (List<CartObj>) session.getAttribute("BOOKLIST");
                            if (bookRoomList != null) {
                                for (CartObj cartObj : bookRoomList) {
                                    cartObj.setDiscountPrice((cartObj.getOriginalPrice() / 100) * (100 - percentOfDiscount));

                                    cartObj.setTotal(cartObj.getDiscountPrice() * cartObj.getAmount());
                                }
                            }
                            
                            double totalCart = 0;
                            for (CartObj cartObj : bookRoomList) {
                                totalCart += cartObj.getTotal();
                            }

                            session.setAttribute("TOTALCART", totalCart);

                            msg = "Apply this discount code " + discountCode + " successfully";
                            request.setAttribute("SUCCESSDISCOUNT", msg);

                            String remindMsg = "Your booking is using the discount code with " + percentOfDiscount + "%";
                            session.setAttribute("REMINDMSG", remindMsg);

                            session.setAttribute("BOOKLIST", bookRoomList);
                        }

                    } else {
                        msg = "This code " + discountCode + " has expired!";
                        request.setAttribute("FAILEDDISCOUNT", msg);
                    }
                } else {
                    msg = "This code " + discountCode + " does not exist!";
                    request.setAttribute("FAILEDDISCOUNT", msg);
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
