package com.example.pokemonqrcode;

/**
 * This is a model class containing user information
 * @author jawad
 * @version final
 */

    /*
     Initialize variables
     */


public class Users {
    //    private String Name;
    private String Username;
    private int Total_Codes;
    private int Total_Score;
    private String Email;
    private String Password;

    public Users() {

    }
//
//    public Users(String username, int total_Codes, int total_Score, String email, String password) {
//        Username = username;
//        Total_Codes = total_Codes;
//        Total_Score = total_Score;
//        Email = email;
//        Password = password;
//    }

    /*
        Following are the getters of the attributes of an object
         */
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
    public String getPassword() {
        return Password;
    }



    public void setTotal_Score(int total_Score) {
        this.Total_Score = total_Score;
    }
    public String getEmail() {
        return this.Email;
    }

    /*
    Following are the setters of the attributes of an object
     */
//    public void setUsername(String username) {
//        Username = username;
//    }
//    public void setTotal_Codes(int total_Codes) {
//        Total_Codes = total_Codes;
//    }
//    public void setTotal_Score(int total_Score) {
//        Total_Score = total_Score;
//    }
    public void setEmail(String email) {
        Email = email;
    }


//    public String getPassword() {
//        return this.Password;
//    }

    public void setPassword(String password) {
        this.Password = password;
    }


    public void setHighest(int Highest) {
        this.Highest = Highest;
    }

};

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
