/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.tbl_DiscountCode;

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
public class Tbl_DiscountCode_DAO implements Serializable {

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

    public boolean checkExistedDiscountCode(String discountCode) throws NamingException, SQLException {
        try {
            int isActive = 0;
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT COUNT(discountCode) as code "
                        + "FROM tbl_DiscountCode "
                        + "WHERE discountCode = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, discountCode);
                rs = stm.executeQuery();

                if (rs.next()) {
                    isActive = rs.getInt("code");
                }

                if (isActive > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public boolean checkExpiryDateOfDiscountCode(String discountCode, String dateTime) throws NamingException, SQLException {
        try {
            int isUsable = 0;
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT COUNT(discountCode) as code "
                        + "FROM tbl_DiscountCode "
                        + "WHERE discountCode = ? AND expiryDate > ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, discountCode);
                stm.setString(2, dateTime);
                rs = stm.executeQuery();

                if (rs.next()) {
                    isUsable = rs.getInt("code");
                }

                if (isUsable > 0) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public int takePercentOfDiscountCode(String discountCode) throws NamingException, SQLException {
        int percentOfDiscount = 0;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT discountPercent "
                        + "FROM tbl_DiscountCode "
                        + "WHERE discountCode = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, discountCode);
                rs = stm.executeQuery();
                if (rs.next()) {
                    percentOfDiscount = rs.getInt("discountPercent");
                }
            }
        } finally {
            closeDB();
        }
        return percentOfDiscount;
    }
    
    public int takeDiscountcodeID(String discountCode) throws NamingException, SQLException {
        int discountID = 0;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT discountCodeID "
                        + "FROM tbl_DiscountCode "
                        + "WHERE discountCode = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, discountCode);
                rs = stm.executeQuery();
                if (rs.next()) {
                    discountID = rs.getInt("discountCodeID");
                }
            }
        } finally {
            closeDB();
        }
        return discountID;
    }
}
