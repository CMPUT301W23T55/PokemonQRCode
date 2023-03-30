package com.example.pokemonqrcode;

import java.util.Date;

public class Users {
//    private String Name;
    private String Username;
    private int Total_Codes;
    private int Total_Score;
    private String Email;
    private String Password;

    private int Highest = 0;

    public int getHighest() {
        return this.Highest;
    }

    public String getUsername() {
        return this.Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public int getTotal_Codes() {
        return this.Total_Codes;
    }

    public void setTotal_Codes(int total_Codes) {
        this.Total_Codes = total_Codes;
    }

    public int getTotal_Score() {
        return this.Total_Score;
    }

    public void setTotal_Score(int total_Score) {
        this.Total_Score = total_Score;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
    public void setHighest(int Highest) {
        this.Highest = Highest;
    }




//    private String Picture;
//    private Date Date;

//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public Users(String name, String picture) {
//        this.Name = name;
//        this.Picture = picture;
//    }
//
//    public Users() {
//    }
//
//    public String getName() {
//        return Name;
//    }
//
//    public void setName(String name) {
//        Name = name;
//    }
//
//    public String getPicture() {
//        return Picture;
//    }
//
//    public void setPicture(String picture) {
//        Picture = picture;
//    }


}
