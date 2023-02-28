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
import java.util.ArrayList;
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
@WebServlet(name = "AddRoomServlet", urlPatterns = {"/AddRoomServlet"})
public class AddRoomServlet extends HttpServlet {

    private final static String LOGIN_PAGE = MyAppConstants.LoginFeature.LOGIN_PAGE;
//    private final static String SEARCH_ACTION = MyAppConstants.SearchHotelFeature.SEARCH_HOTEL_ACTION;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");

        String url = (String) siteMap.get(MyAppConstants.SearchHotelFeature.SEARCH_HOTEL_ACTION);

        HttpSession session = request.getSession();

        String lastHotel = request.getParameter("lastHotelNameSearch");
        String lastCheckin = request.getParameter("checkInDateSearch");
        String lastCheckout = request.getParameter("checkOutDateSearch");
        String lastRoom = request.getParameter("roomSearch");

        String txtRoomID = request.getParameter("txtRoomIDDTO");
        String txtHotelName = request.getParameter("txtHotelNameDTO");
        String txtRoomType = request.getParameter("txtRoomTypeDTO");
        String txtPrice = request.getParameter("txtPriceDTO");

        String msg = "";

        boolean isFound = false;
        try {
            String customer = (String) session.getAttribute("CUSTOMER");
            if (customer == null) {
                url = LOGIN_PAGE;
                response.sendRedirect(url);
            } else {
                int defaultDiscount = 0;
                int defaultDiscountID = 0;
                double totalCart = 0;
                // Đặt phòng khi đã tìm kiếm 
                if ((lastHotel != null && lastHotel.trim().length() > 0)
                        && (lastCheckin != null && lastCheckin.trim().length() > 0)
                        && (lastCheckout != null && lastCheckout.trim().length() > 0)) {

                    List<CartObj> bookRoomList;
                    CartObj bookRoom = new CartObj(Integer.parseInt(txtRoomID), txtHotelName, txtRoomType, 1,
                            Double.parseDouble(txtPrice), Double.parseDouble(txtPrice), Double.parseDouble(txtPrice));

                    bookRoomList = (List<CartObj>) session.getAttribute("BOOKLIST");
                    if (bookRoomList == null) {
                        session.setAttribute("PERCENTOFDISCOUNT", defaultDiscount);
                        session.setAttribute("DISCOUNTCODEID", defaultDiscountID);

                        bookRoomList = new ArrayList<>();
                        bookRoomList.add(bookRoom);
                    } else {
                        int percentOfDiscount = 0;
                        percentOfDiscount = (Integer) session.getAttribute("PERCENTOFDISCOUNT");
                        
                        // Trường hợp đã xài mã giảm giá
                        if (percentOfDiscount > 0) {
                            for (CartObj cart : bookRoomList) {
                                if (cart.getHotelName().equals(txtHotelName) && (cart.getRoomType().equals(txtRoomType))) {
                                    isFound = true;
                                    cart.setAmount(cart.getAmount() + 1);
                                    cart.setDiscountPrice((cart.getOriginalPrice() / 100) * (100 - percentOfDiscount));
                                    cart.setTotal(cart.getDiscountPrice() * cart.getAmount());
                                    break;
                                } else {
                                    isFound = false;
                                }
                            }
                            if (!isFound) {
                                bookRoom.setDiscountPrice((bookRoom.getOriginalPrice() / 100) * (100 - percentOfDiscount));
                                bookRoom.setTotal(bookRoom.getDiscountPrice() * bookRoom.getAmount());
                                bookRoomList.add(bookRoom);
                            }
                        } else {
                            for (CartObj cart : bookRoomList) {
                                if (cart.getHotelName().equals(txtHotelName) && (cart.getRoomType().equals(txtRoomType))) {
                                    isFound = true;
                                    cart.setAmount(cart.getAmount() + 1);
                                    cart.setTotal(cart.getDiscountPrice() * cart.getAmount());
                                    break;
                                } else {
                                    isFound = false;
                                }
                            }
                            if (!isFound) {
                                bookRoomList.add(bookRoom);
                            }
                        }

                        for (CartObj cartObj : bookRoomList) {
                            totalCart += cartObj.getTotal();
                        }

                    }
                    session.setAttribute("BOOKLIST", bookRoomList);
                    session.setAttribute("TOTALCART", totalCart);

                    url = "HotelSearchServlet?"
                            + "&txtHotelName=" + lastHotel
                            + "&txtCheckin=" + lastCheckin
                            + "&txtCheckout=" + lastCheckout
                            + "&txtRoom=" + lastRoom;

                } else {
                    // Đặt phòng khi chưa tìm kiếm 

                    List<CartObj> bookRoomList;
                    CartObj bookRoom = new CartObj(Integer.parseInt(txtRoomID), txtHotelName, txtRoomType, 1,
                            Double.parseDouble(txtPrice), Double.parseDouble(txtPrice), Double.parseDouble(txtPrice));

                    bookRoomList = (List<CartObj>) session.getAttribute("BOOKLIST");
                    if (bookRoomList == null) {
                        session.setAttribute("PERCENTOFDISCOUNT", defaultDiscount);
                        session.setAttribute("DISCOUNTCODEID", defaultDiscountID);

                        bookRoomList = new ArrayList<>();
                        bookRoomList.add(bookRoom);
                    } else {
                        int percentOfDiscount = 0;
                        percentOfDiscount = (Integer) session.getAttribute("PERCENTOFDISCOUNT");
                        
                        // Trường hợp đã xài mã giảm giá
                        if (percentOfDiscount > 0) {
                            for (CartObj cart : bookRoomList) {
                                if (cart.getHotelName().equals(txtHotelName) && (cart.getRoomType().equals(txtRoomType))) {
                                    isFound = true;
                                    cart.setAmount(cart.getAmount() + 1);
                                    cart.setDiscountPrice((cart.getOriginalPrice() / 100) * (100 - percentOfDiscount));
                                    cart.setTotal(cart.getDiscountPrice() * cart.getAmount());
                                    break;
                                } else {
                                    isFound = false;
                                }
                            }
                            if (!isFound) {
                                bookRoom.setDiscountPrice((bookRoom.getOriginalPrice() / 100) * (100 - percentOfDiscount));
                                bookRoom.setTotal(bookRoom.getDiscountPrice() * bookRoom.getAmount());
                                bookRoomList.add(bookRoom);
                            }
                        } else {
                            for (CartObj cart : bookRoomList) {
                                if (cart.getHotelName().equals(txtHotelName) && (cart.getRoomType().equals(txtRoomType))) {
                                    isFound = true;
                                    cart.setAmount(cart.getAmount() + 1);
                                    cart.setTotal(cart.getDiscountPrice() * cart.getAmount());
                                    break;
                                } else {
                                    isFound = false;
                                }
                            }
                            if (!isFound) {                              
                                bookRoomList.add(bookRoom);
                            }
                        }
                    }
                    session.setAttribute("BOOKLIST", bookRoomList);

                    for (CartObj cartObj : bookRoomList) {
                        totalCart += cartObj.getTotal();
                    }

                    session.setAttribute("TOTALCART", totalCart);

                }
                msg = "Add room successfully!";
                request.setAttribute("ADDROOMRESULT", msg);
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
        } finally {
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
