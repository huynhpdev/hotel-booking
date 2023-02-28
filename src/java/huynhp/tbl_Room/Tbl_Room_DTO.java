/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.tbl_Room;

import java.io.Serializable;

/**
 *
 * @author MSI
 */
public class Tbl_Room_DTO implements Serializable {
    
    private int hotelID, roomID;
    private String hotelName, roomType;
    private int availableRoom;
    private double price;
    private String checkInDate, checkOutDate;

    public Tbl_Room_DTO() {

    }

    public Tbl_Room_DTO(int hotelID, int roomID, String hotelName, String roomType, int availableRoom, double price, String checkInDate, String checkOutDate) {
        this.hotelID = hotelID;
        this.roomID = roomID;
        this.hotelName = hotelName;
        this.roomType = roomType;
        this.availableRoom = availableRoom;
        this.price = price;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

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

    public int getAvailableRoom() {
        return availableRoom;
    }

    public void setAvailableRoom(int availableRoom) {
        this.availableRoom = availableRoom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    
}
