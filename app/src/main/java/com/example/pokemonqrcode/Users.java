package com.example.pokemonqrcode;

/**
 * This is a model class containing user information
 * @author jawad
 * @version final
 */
public class Users {
    /*
     Initialize variables
     */
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
    public String getUsername() {
        return Username;
    }
    public int getTotal_Codes() {
        return Total_Codes;
    }
    public int getTotal_Score() {
        return Total_Score;
    }
    public String getPassword() {
        return Password;
    }
    public String getEmail() {
        return Email;
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
    public void setPassword(String password) {
        Password = password;
    }

}
