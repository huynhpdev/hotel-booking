/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.tbl_QualityRating;

import huynhp.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author MSI
 */
public class Tbl_QualityRating_DAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

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

    public boolean feedbackBookingRoom(int roomID, int userInfoID, String userFeedback, int userRating) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "INSERT INTO tbl_QualityRating(roomID, userInfoID, feedback, ratingScore) "
                    + "VALUES (?,?,?,?)";
                stm = con.prepareStatement(sql);                
                stm.setInt(1, roomID);
                stm.setInt(2, userInfoID);
                stm.setString(3, userFeedback);
                stm.setInt(4, userRating);

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
