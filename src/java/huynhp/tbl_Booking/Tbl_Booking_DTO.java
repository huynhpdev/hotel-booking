/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.tbl_Booking;

import java.io.Serializable;

/**
 *
 * @author MSI
 */
public class Tbl_Booking_DTO implements Serializable {
    private int bookingID, roomID, confirmStatus;
    private String hotelName, roomType, checkInDate, checkOutDate, bookDate;
    private int amount, percentOfDiscount;
    private double price, total;
    private boolean allowFeedback;

    public Tbl_Booking_DTO() {
    }

    public Tbl_Booking_DTO(int bookingID, int roomID, int confirmStatus, String hotelName, String roomType, 
            String checkInDate, String checkOutDate, String bookDate, 
            int amount, int percentOfDiscount, double price, double total, boolean allowFeedback) {
        this.bookingID = bookingID;
        this.roomID = roomID;
        this.confirmStatus = confirmStatus;
        this.hotelName = hotelName;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookDate = bookDate;
        this.amount = amount;
        this.percentOfDiscount = percentOfDiscount;
        this.price = price;
        this.total = total;
        this.allowFeedback = allowFeedback;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(int confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }  

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPercentOfDiscount() {
        return percentOfDiscount;
    }

    public void setPercentOfDiscount(int percentOfDiscount) {
        this.percentOfDiscount = percentOfDiscount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isAllowFeedback() {
        return allowFeedback;
    }

    public void setAllowFeedback(boolean allowFeedback) {
        this.allowFeedback = allowFeedback;
    }
}
