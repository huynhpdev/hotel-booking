package huynhp.tbl_User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MSI
 */
public class Tbl_User_DTO {
    private String email, name;
    private int userInfoID, roleID, statusID;

    public Tbl_User_DTO() {
    }

    public Tbl_User_DTO(String email, String name, int roleID, int statusID, int userInfoID) {
        this.email = email;
        this.name = name;
        this.roleID = roleID;
        this.statusID = statusID;
        this.userInfoID = userInfoID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public int getUserInfoID() {
        return userInfoID;
    }

    public void setUserInfoID(int userInfoID) {
        this.userInfoID = userInfoID;
    }
        
}
