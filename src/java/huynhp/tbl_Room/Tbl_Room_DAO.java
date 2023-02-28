/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.tbl_Room;

import huynhp.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author MSI
 */
public class Tbl_Room_DAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
        
    List<Tbl_Room_DTO> availableRoomList;

    public List<Tbl_Room_DTO> getAvailableRoomList() {
        return availableRoomList;
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
    
    public void getAllAvailableRoomInToday(String systemToday) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT h.hotelID, h.hotelName, rt.name, r.roomID, r.availableRoom, r.price, r.checkInDate, r.checkOutDate "
                        + "FROM tbl_Hotel AS h "
                        + "INNER JOIN tbl_Room AS r ON h.hotelID = r.hotelID "
                        + "INNER JOIN tbl_RoomType AS rt ON r.roomTypeID = rt.id "
                        + "WHERE r.availableRoom > 0 AND r.checkInDate >= ? AND r.checkOutDate > ? "
                        + "ORDER BY h.hotelName";
                stm = con.prepareStatement(sql);
                stm.setString(1, systemToday);
                stm.setString(2, systemToday);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Tbl_Room_DTO dto = new Tbl_Room_DTO(rs.getInt("hotelID"), rs.getInt("roomID"), 
                            rs.getString("hotelName"), rs.getString("name"), 
                            rs.getInt("availableRoom"), rs.getDouble("price"), 
                            DBHelper.convertDateTime(rs.getString("checkInDate")),
                            DBHelper.convertDateTime(rs.getString("checkOutDate")));
                    
                    if (this.availableRoomList == null) {
                        this.availableRoomList = new ArrayList<>();
                    }
                    
                    this.availableRoomList.add(dto);
                }          
            }
        } finally {
            closeDB();
        }
    } 
    
    public void getAllAvailableRoomByCICO(String hotelName, int room, String checkinDate, String checkoutDate) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT h.hotelID, h.hotelName, rt.name, r.roomID, r.availableRoom, r.price, r.checkInDate, r.checkOutDate "
                        + "FROM tbl_Hotel AS h "
                        + "INNER JOIN tbl_Room AS r ON h.hotelID = r.hotelID "
                        + "INNER JOIN tbl_RoomType AS rt ON r.roomTypeID = rt.id "
                        + "WHERE h.hotelName LIKE ? AND r.availableRoom >= ? AND r.checkInDate >= ? AND r.checkOutDate <= ? "
                        + "ORDER BY h.hotelName";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + hotelName + "%");
                stm.setInt(2, room);
                stm.setString(3, checkinDate);
                stm.setString(4, checkoutDate);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Tbl_Room_DTO dto = new Tbl_Room_DTO(rs.getInt("hotelID"), rs.getInt("roomID"), 
                            rs.getString("hotelName"), rs.getString("name"), 
                            rs.getInt("availableRoom"), rs.getDouble("price"), 
                            DBHelper.convertDateTime(rs.getString("checkInDate")),
                            DBHelper.convertDateTime(rs.getString("checkOutDate")));
                    
                    if (this.availableRoomList == null) {
                        this.availableRoomList = new ArrayList<>();
                    }
                    
                    this.availableRoomList.add(dto);
                }          
            }
        } finally {
            closeDB();
        }
    } 
    
    public boolean checkAvailableRoom(int roomID, int quantityRoom) throws NamingException, SQLException {       
        try {
            int isAvailable = 0;
            con = DBHelper.getConnect();
            if (con != null) {             
                String sql = "SELECT count(availableRoom) as room "
                        + "FROM tbl_Room "
                        + "WHERE roomID = ? AND availableRoom >= ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, roomID);
                stm.setInt(2, quantityRoom);
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    isAvailable = rs.getInt("room");
                }
                
                if (isAvailable > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }
    
    public boolean getBookingRoomAndDecreaseAvailableRoom(int roomID, int quantityRoom) 
            throws NamingException, SQLException {       
        try {
            con = DBHelper.getConnect();
            if (con != null) {             
                String sql = "UPDATE tbl_Room "
                        + "SET availableRoom=(availableRoom - ?) "
                        + "WHERE roomID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, quantityRoom);
                stm.setInt(2, roomID);
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
