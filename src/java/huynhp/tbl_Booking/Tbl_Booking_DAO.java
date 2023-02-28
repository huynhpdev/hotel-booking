/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.tbl_Booking;

import huynhp.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author MSI
 */
public class Tbl_Booking_DAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    List<Tbl_Booking_DTO> bookingList;

    public List<Tbl_Booking_DTO> getBookingList() {
        return bookingList;
    }

    private void closeDB() throws NamingException, SQLException {
        if (rs != null) {
            rs.close();
        }

        if (stm != null) {
            stm.close();
        }

        if (con != null) {
            con.close();
        }
    }

    public boolean checkConditionToFeedback(String checkoutDateInput, int isFeedback) throws ParseException {
        boolean correctDateToFeedback = DBHelper.compareCheckoutDateWithCurrentDate(checkoutDateInput);
        boolean isAllowedFeedback = false;
        if (correctDateToFeedback && isFeedback == 1) {
            isAllowedFeedback = true;
        }
        return isAllowedFeedback;
    }

    public void getAllBookingHistoryByUser(int userInfoID) throws NamingException, SQLException, ParseException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT h.hotelName, ry.name, b.amount, r.roomID, r.price, b.total, "
                        + "b.confirmStatus, r.checkInDate, r.checkOutDate, "
                        + "b.bookDate, b.bookingID, b.isFeedback, dc.discountPercent "
                        + "FROM tbl_Booking AS b "
                        + "INNER JOIN tbl_Room AS r ON b.roomID = r.roomID "
                        + "INNER JOIN tbl_Hotel AS h ON h.hotelID = r.hotelID "
                        + "INNER JOIN tbl_RoomType AS ry ON r.roomTypeID = ry.id "
                        + "LEFT JOIN tbl_DiscountCode AS dc ON dc.discountCodeID = b.discountCodeID "
                        + "INNER JOIN tbl_UserInfo AS ui ON ui.userInfoID = b.userInfoID "
                        + "WHERE b.userInfoID = ? AND b.bookingStatus = 0 "
                        + "ORDER BY b.bookDate";
                stm = con.prepareStatement(sql);
                stm.setInt(1, userInfoID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int percentOfDiscount = rs.getInt("discountPercent");
                    Tbl_Booking_DTO dto = null;
                    if (rs.wasNull()) {
                        dto = new Tbl_Booking_DTO(rs.getInt("bookingID"), rs.getInt("roomID"),
                                rs.getInt("confirmStatus"), rs.getString("hotelName"), rs.getString("name"),
                                DBHelper.convertDateTime(rs.getString("checkInDate")),
                                DBHelper.convertDateTime(rs.getString("checkOutDate")),
                                DBHelper.convertDateTime(rs.getString("bookDate")), rs.getInt("amount"), 0,
                                rs.getDouble("price"), rs.getDouble("total"),
                                checkConditionToFeedback(rs.getString("checkOutDate"), rs.getInt("isFeedback")));
                    } else {
                        dto = new Tbl_Booking_DTO(rs.getInt("bookingID"), rs.getInt("roomID"),
                                rs.getInt("confirmStatus"), rs.getString("hotelName"), rs.getString("name"),
                                DBHelper.convertDateTime(rs.getString("checkInDate")),
                                DBHelper.convertDateTime(rs.getString("checkOutDate")),
                                DBHelper.convertDateTime(rs.getString("bookDate")), rs.getInt("amount"),
                                rs.getInt("discountPercent"), rs.getDouble("price"), rs.getDouble("total"),
                                checkConditionToFeedback(rs.getString("checkOutDate"), rs.getInt("isFeedback")));
                    }

                    if (this.bookingList == null) {
                        this.bookingList = new ArrayList<>();
                    }

                    this.bookingList.add(dto);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getAllBookingHistoryByHotelName(int userInfoID, String hotelName) throws NamingException, SQLException, ParseException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT h.hotelName, ry.name, b.amount, r.roomID, r.price, b.total, "
                        + "b.confirmStatus, r.checkInDate, r.checkOutDate, b.bookDate, b.isFeedback, "
                        + "b.bookingID, dc.discountPercent "
                        + "FROM tbl_Booking AS b "
                        + "INNER JOIN tbl_Room AS r ON b.roomID = r.roomID "
                        + "INNER JOIN tbl_Hotel AS h ON h.hotelID = r.hotelID "
                        + "INNER JOIN tbl_RoomType AS ry ON r.roomTypeID = ry.id "
                        + "LEFT JOIN tbl_DiscountCode AS dc ON dc.discountCodeID = b.discountCodeID "
                        + "INNER JOIN tbl_UserInfo AS ui ON ui.userInfoID = b.userInfoID "
                        + "WHERE b.userInfoID = ? AND h.hotelName LIKE ? AND b.bookingStatus = 0 "
                        + "ORDER BY b.bookDate";
                stm = con.prepareStatement(sql);
                stm.setInt(1, userInfoID);
                stm.setString(2, "%" + hotelName + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    int percentOfDiscount = rs.getInt("discountPercent");
                    Tbl_Booking_DTO dto = null;
                    if (rs.wasNull()) {
                        dto = new Tbl_Booking_DTO(rs.getInt("bookingID"), rs.getInt("roomID"),
                                rs.getInt("confirmStatus"), rs.getString("hotelName"), rs.getString("name"),
                                DBHelper.convertDateTime(rs.getString("checkInDate")),
                                DBHelper.convertDateTime(rs.getString("checkOutDate")),
                                DBHelper.convertDateTime(rs.getString("bookDate")), rs.getInt("amount"), 0,
                                rs.getDouble("price"), rs.getDouble("total"),
                                checkConditionToFeedback(rs.getString("checkOutDate"), rs.getInt("isFeedback")));
                    } else {
                        dto = new Tbl_Booking_DTO(rs.getInt("bookingID"), rs.getInt("roomID"),
                                rs.getInt("confirmStatus"), rs.getString("hotelName"), rs.getString("name"),
                                DBHelper.convertDateTime(rs.getString("checkInDate")),
                                DBHelper.convertDateTime(rs.getString("checkOutDate")),
                                DBHelper.convertDateTime(rs.getString("bookDate")), rs.getInt("amount"),
                                rs.getInt("discountPercent"), rs.getDouble("price"), rs.getDouble("total"),
                                checkConditionToFeedback(rs.getString("checkOutDate"), rs.getInt("isFeedback")));
                    }

                    if (this.bookingList == null) {
                        this.bookingList = new ArrayList<>();
                    }

                    this.bookingList.add(dto);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getAllBookingHistoryByBookingDate(int userInfoID, String bookingDate) throws NamingException, SQLException, ParseException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT h.hotelName, ry.name, b.amount, r.roomID, r.price, b.total, "
                        + "b.confirmStatus, r.checkInDate, r.checkOutDate, b.bookDate, b.isFeedback, "
                        + "b.bookingID, dc.discountPercent "
                        + "FROM tbl_Booking AS b "
                        + "INNER JOIN tbl_Room AS r ON b.roomID = r.roomID "
                        + "INNER JOIN tbl_Hotel AS h ON h.hotelID = r.hotelID "
                        + "INNER JOIN tbl_RoomType AS ry ON r.roomTypeID = ry.id "
                        + "LEFT JOIN tbl_DiscountCode AS dc ON dc.discountCodeID = b.discountCodeID "
                        + "INNER JOIN tbl_UserInfo AS ui ON ui.userInfoID = b.userInfoID "
                        + "WHERE b.userInfoID = ? AND b.bookDate = ? AND b.bookingStatus = 0 "
                        + "ORDER BY b.bookDate";
                stm = con.prepareStatement(sql);
                stm.setInt(1, userInfoID);
                stm.setString(2, bookingDate);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int percentOfDiscount = rs.getInt("discountPercent");
                    Tbl_Booking_DTO dto = null;
                    if (rs.wasNull()) {
                        dto = new Tbl_Booking_DTO(rs.getInt("bookingID"), rs.getInt("roomID"),
                                rs.getInt("confirmStatus"), rs.getString("hotelName"), rs.getString("name"),
                                DBHelper.convertDateTime(rs.getString("checkInDate")),
                                DBHelper.convertDateTime(rs.getString("checkOutDate")),
                                DBHelper.convertDateTime(rs.getString("bookDate")), rs.getInt("amount"), 0,
                                rs.getDouble("price"), rs.getDouble("total"),
                                checkConditionToFeedback(rs.getString("checkOutDate"), rs.getInt("isFeedback")));
                    } else {
                        dto = new Tbl_Booking_DTO(rs.getInt("bookingID"), rs.getInt("roomID"),
                                rs.getInt("confirmStatus"), rs.getString("hotelName"), rs.getString("name"),
                                DBHelper.convertDateTime(rs.getString("checkInDate")),
                                DBHelper.convertDateTime(rs.getString("checkOutDate")),
                                DBHelper.convertDateTime(rs.getString("bookDate")), rs.getInt("amount"),
                                rs.getInt("discountPercent"), rs.getDouble("price"), rs.getDouble("total"),
                                checkConditionToFeedback(rs.getString("checkOutDate"), rs.getInt("isFeedback")));
                    }

                    if (this.bookingList == null) {
                        this.bookingList = new ArrayList<>();
                    }

                    this.bookingList.add(dto);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getAllBookingHistoryByBookingDateAndHotelName(int userInfoID, String hotelName, String bookingDate) throws NamingException, SQLException, ParseException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT h.hotelName, ry.name, b.amount, r.roomID, r.price, b.total, "
                        + "b.confirmStatus, r.checkInDate, r.checkOutDate, b.bookDate, b.isFeedback, "
                        + "b.bookingID, dc.discountPercent "
                        + "FROM tbl_Booking AS b "
                        + "INNER JOIN tbl_Room AS r ON b.roomID = r.roomID "
                        + "INNER JOIN tbl_Hotel AS h ON h.hotelID = r.hotelID "
                        + "INNER JOIN tbl_RoomType AS ry ON r.roomTypeID = ry.id "
                        + "LEFT JOIN tbl_DiscountCode AS dc ON dc.discountCodeID = b.discountCodeID "
                        + "INNER JOIN tbl_UserInfo AS ui ON ui.userInfoID = b.userInfoID "
                        + "WHERE b.userInfoID = ? AND b.bookDate = ? AND h.hotelName LIKE ? AND b.bookingStatus = 0 "
                        + "ORDER BY b.bookDate";
                stm = con.prepareStatement(sql);
                stm.setInt(1, userInfoID);
                stm.setString(2, bookingDate);
                stm.setString(3, "%" + hotelName + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    int percentOfDiscount = rs.getInt("discountPercent");
                    Tbl_Booking_DTO dto = null;
                    if (rs.wasNull()) {
                        dto = new Tbl_Booking_DTO(rs.getInt("bookingID"), rs.getInt("roomID"),
                                rs.getInt("confirmStatus"), rs.getString("hotelName"), rs.getString("name"),
                                DBHelper.convertDateTime(rs.getString("checkInDate")),
                                DBHelper.convertDateTime(rs.getString("checkOutDate")),
                                DBHelper.convertDateTime(rs.getString("bookDate")), rs.getInt("amount"), 0,
                                rs.getDouble("price"), rs.getDouble("total"),
                                checkConditionToFeedback(rs.getString("checkOutDate"), rs.getInt("isFeedback")));
                    } else {
                        dto = new Tbl_Booking_DTO(rs.getInt("bookingID"), rs.getInt("roomID"),
                                rs.getInt("confirmStatus"), rs.getString("hotelName"), rs.getString("name"),
                                DBHelper.convertDateTime(rs.getString("checkInDate")),
                                DBHelper.convertDateTime(rs.getString("checkOutDate")),
                                DBHelper.convertDateTime(rs.getString("bookDate")), rs.getInt("amount"),
                                rs.getInt("discountPercent"), rs.getDouble("price"), rs.getDouble("total"),
                                checkConditionToFeedback(rs.getString("checkOutDate"), rs.getInt("isFeedback")));;
                    }

                    if (this.bookingList == null) {
                        this.bookingList = new ArrayList<>();
                    }

                    this.bookingList.add(dto);
                }
            }
        } finally {
            closeDB();
        }
    }

    public boolean deleteBookingHistory(int bookingID) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "UPDATE tbl_Booking "
                        + "SET bookingStatus = 1 "
                        + "WHERE bookingID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, bookingID);

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public boolean setFeedbackStatus(int bookingID) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "UPDATE tbl_Booking "
                        + "SET isFeedback = 0 "
                        + "WHERE bookingID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, bookingID);

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public boolean bookRoom(int userInfoID, int roomID, int discountCodeID,
            int amount, String bookDate, double total, int bookingStatus,
            int confirmStatus, int isFeedback) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "";
                if (discountCodeID > 0) {
                    sql = "INSERT tbl_Booking(userInfoID, roomID, discountCodeID, amount, bookDate, "
                            + "total, bookingStatus, confirmStatus, isFeedback) "
                            + "VALUES (?,?,?,?,?,?,?,?,?)";
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, userInfoID);
                    stm.setInt(2, roomID);
                    stm.setInt(3, discountCodeID);
                    stm.setInt(4, amount);
                    stm.setString(5, bookDate);
                    stm.setDouble(6, total);
                    stm.setInt(7, bookingStatus);
                    stm.setInt(8, confirmStatus);
                    stm.setInt(9, isFeedback);
                } else {
                    sql = "INSERT tbl_Booking(userInfoID, roomID, amount, bookDate, "
                            + "total, bookingStatus, confirmStatus, isFeedback) "
                            + "VALUES (?,?,?,?,?,?,?,?)";
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, userInfoID);
                    stm.setInt(2, roomID);
                    stm.setInt(3, amount);
                    stm.setString(4, bookDate);
                    stm.setDouble(5, total);
                    stm.setInt(6, bookingStatus);
                    stm.setInt(7, confirmStatus);
                    stm.setInt(8, isFeedback);
                }

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public int getBookingID(int userInfoID, int roomID) throws NamingException, SQLException {
        int bookingID = 0;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT MAX(bookingID) as id "
                        + "FROM tbl_Booking "
                        + "WHERE userInfoID = ? AND roomID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, userInfoID);
                stm.setInt(2, roomID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    bookingID = rs.getInt("id");
                }
            }
        } finally {
            closeDB();
        }
        return bookingID;
    }

    public boolean confirmBooking(int bookingID) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "UPDATE tbl_Booking "
                        + "SET confirmStatus = 0 "
                        + "WHERE bookingID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, bookingID);

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }
}
