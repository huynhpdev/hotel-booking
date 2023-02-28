/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.cart;

import java.io.Serializable;

/**
 *
 * @author MSI
 */
public class CartObj implements Serializable {

    private int roomID;
    private String hotelName, roomType;
    private int amount;
    private double originalPrice, discountPrice, total;

    public CartObj() {
    }

    public CartObj(int roomID, String hotelName, String roomType, int amount,
            double originalPrice, double discountPrice, double total) {
        this.roomID = roomID;
        this.hotelName = hotelName;
        this.roomType = roomType;
        this.amount = amount;
        
        this.originalPrice = originalPrice;
        this.discountPrice = discountPrice;
        this.total = total;
    }  
            
//    public CartObj(CartObj co) {
//        this.roomID = co.roomID;
//        this.hotelName = co.hotelName;
//        this.roomType = co.roomType;
//        this.amount = co.amount;
//        this.originalPrice = co.originalPrice;
//        this.discountPrice = co.discountPrice;
//        this.total = co.total;
//    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
