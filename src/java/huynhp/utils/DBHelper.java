/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.utils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author MSI
 */
public class DBHelper implements Serializable {

    private static final DateTimeFormatter yyyymmddFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter ddmmyyFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final LocalDate localDate = LocalDate.now();  
    
    public static Connection getConnect() throws NamingException, SQLException {
        Context context = new InitialContext();
        Context tomcatContext = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcatContext.lookup("HotelBooking");
        return ds.getConnection();
    }

    public static String encryptPass(String base) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(base.getBytes("UTF-8"));
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static String convertDateTime(String inputDateTime) {
        String correctDateTime = "";
        correctDateTime = LocalDate.parse(inputDateTime, yyyymmddFormat).format(ddmmyyFormat);
        return correctDateTime;
    }

    public static String convertDateTimeFromUserInput(String inputDateTime) {
        String correctDateTime = "";
        correctDateTime = LocalDate.parse(inputDateTime, ddmmyyFormat).format(yyyymmddFormat);
        return correctDateTime;
    }

    public static boolean compareCheckoutDateWithCurrentDate(String checkoutDateInput) throws ParseException {
        boolean isLarger = false;
        Date checkoutDate = sdf.parse(checkoutDateInput);
        Date currentDate = sdf.parse(localDate.toString());
        if (currentDate.after(checkoutDate)) {
            isLarger = true;
        }
        return isLarger;
    }
    
}
