/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.tbl_User;

import huynhp.utils.DBHelper;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author MSI
 */
public class Tbl_User_DAO {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    List<Tbl_User_DTO> accountList;

    public List<Tbl_User_DTO> getAccountList() {
        return accountList;
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

    public Tbl_User_DTO checkLogin(String userEmail, String userPassword) throws NamingException,
            SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT email, roleID, statusID, ui.name, ui.userInfoID "
                        + "FROM tbl_User AS u "
                        + "INNER JOIN tbl_UserInfo as ui ON ui.userID = u.userID "
                        + "WHERE email = ? AND password = ? AND statusID = 0";

                stm = con.prepareStatement(sql);
                stm.setString(1, userEmail);
                stm.setString(2, DBHelper.encryptPass(userPassword));
                rs = stm.executeQuery();

                if (rs.next()) {
                    return new Tbl_User_DTO(rs.getString("email"), rs.getString("name"),
                            rs.getInt("roleID"), rs.getInt("statusID"), rs.getInt("userInfoID"));
                }
            }
        } finally {
            closeDB();
        }
        return null;
    }

    public boolean checkAccountStatus(String userEmail) throws NamingException, SQLException {
        try {
            int isActive = 0;
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT COUNT(email) as countEmail "
                        + "FROM tbl_User "
                        + "WHERE email = ? AND statusID = 0";
                stm = con.prepareStatement(sql);
                stm.setString(1, userEmail);
                rs = stm.executeQuery();
                if (rs.next()) {
                    isActive = rs.getInt("countEmail");
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

    public boolean checkExistedEmail(String userEmail) throws NamingException, SQLException {
        try {
            int isExisted = 0;
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT COUNT(email) as countEmail "
                        + "FROM tbl_User "
                        + "WHERE email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userEmail);
                rs = stm.executeQuery();
                if (rs.next()) {
                    isExisted = rs.getInt("countEmail");
                }

                if (isExisted > 0) {
                    return true;
                }
            }

        } finally {
            closeDB();
        }
        return false;
    }

    public boolean registerNewAccount(String userEmail, String userPassword,
            int roleID, int statusID, String fullName,
            String address, String phone, String createdDate) throws NamingException, SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            con = DBHelper.getConnect();
            String sql = "";
            if (con != null) {
                sql = "INSERT INTO tbl_User(email, password, roleID, statusID) "
                        + "VALUES (?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, userEmail);
                stm.setString(2, DBHelper.encryptPass(userPassword));
                stm.setInt(3, roleID);
                stm.setInt(4, statusID);

                int row = stm.executeUpdate();

                if (row > 0) {
                    int userID = 0;
                    sql = "SELECT userID "
                            + "FROM tbl_User "
                            + "WHERE email = ?";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, userEmail);
                    rs = stm.executeQuery();
                    if (rs.next()) {
                        userID = rs.getInt("userID");
                    }

                    if (userID > 0) {
                        sql = "INSERT INTO tbl_UserInfo(userID, phone, name, address, createdDate)"
                                + "VALUES (?,?,?,?,?)";
                        stm = con.prepareStatement(sql);
                        stm.setInt(1, userID);
                        stm.setString(2, phone);
                        stm.setString(3, fullName);
                        stm.setString(4, address);
                        stm.setString(5, createdDate);
                        int userInfoRow = stm.executeUpdate();
                        if (userInfoRow > 0) {
                            return true;
                        }
                    }
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

}
