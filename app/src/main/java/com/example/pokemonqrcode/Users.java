package com.example.pokemonqrcode;

<<<<<<< HEAD
/**
 * This is a model class containing user information
 * @author jawad
 * @version final
 */
public class Users {
    /*
     Initialize variables
     */
=======
import java.util.Comparator;
import java.util.Date;

public class Users {
    //    private String Name;
>>>>>>> 72fae0f0a2e1aec6df55378bcd9e85be9e58d3dd
    private String Username;
    private int Total_Codes;
    private int Total_Score;
    private String Email;
    private String Password;

<<<<<<< HEAD
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
=======
    private int Highest = 0;

    public int getHighest() {
        return this.Highest;
    }

>>>>>>> 72fae0f0a2e1aec6df55378bcd9e85be9e58d3dd
    public String getUsername() {
        return this.Username;
    }
<<<<<<< HEAD
=======

    public void setUsername(String username) {
        this.Username = username;
    }

>>>>>>> 72fae0f0a2e1aec6df55378bcd9e85be9e58d3dd
    public int getTotal_Codes() {
        return this.Total_Codes;
    }
<<<<<<< HEAD
=======

    public void setTotal_Codes(int total_Codes) {
        this.Total_Codes = total_Codes;
    }

>>>>>>> 72fae0f0a2e1aec6df55378bcd9e85be9e58d3dd
    public int getTotal_Score() {
        return this.Total_Score;
    }
<<<<<<< HEAD
    public String getPassword() {
        return Password;
=======


    public void setTotal_Score(int total_Score) {
        this.Total_Score = total_Score;
>>>>>>> 72fae0f0a2e1aec6df55378bcd9e85be9e58d3dd
    }
    public String getEmail() {
        return this.Email;
    }

    /*
    Following are the setters of the attributes of an object
     */
    public void setUsername(String username) {
        Username = username;
    }
    public void setTotal_Codes(int total_Codes) {
        Total_Codes = total_Codes;
    }
    public void setTotal_Score(int total_Score) {
        Total_Score = total_Score;
    }
    public void setEmail(String email) {
        Email = email;
    }
<<<<<<< HEAD
=======

    public String getPassword() {
        return this.Password;
    }

>>>>>>> 72fae0f0a2e1aec6df55378bcd9e85be9e58d3dd
    public void setPassword(String password) {
        this.Password = password;
    }

<<<<<<< HEAD
}
=======
    public void setHighest(int Highest) {
        this.Highest = Highest;
    }

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
>>>>>>> 72fae0f0a2e1aec6df55378bcd9e85be9e58d3dd
