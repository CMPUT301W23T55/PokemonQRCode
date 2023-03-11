package com.example.pokemonqrcode;


import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;


import java.util.ArrayList;

import java.util.Date;
import java.util.List;

/**
 * This class is what shows up on the player profile
 * Create custom ListView adapter similar to GasStation in Assignment1
 */

public class PlayerCode {
    private String codeHash;
    private int score;
    private String name;
    private Date date;
    private String picture;
    private Bitmap photo;
    private List<Address> location;
    private ArrayList<String> comments;


    public PlayerCode(ScannedCode code, Bitmap photo, List<Address> location) {
        this.name = code.getName();
        this.score = code.getScore();
        this.picture = code.getPicture();
        this.photo = photo;
        this.location = location;
    }

    /*

    name
    score
    date
    hashcode
    picture
    geolocation
    comment
     */


    public PlayerCode(String hash, String name, int score, String image) {
        this.codeHash = hash;
        this.name = name;
        this.score = score;
        this.picture = image;
        this.date = new Date();
    }

    public PlayerCode(String hash, String name, int score, String image, Date date) {
        this.codeHash = hash;
        this.name = name;
        this.score = score;
        this.picture = image;
        this.date = date;
    }

    public PlayerCode(String hash, String name, int score, String image,
                      Date date, ArrayList<String>comments) {
        this.codeHash = hash;
        this.name = name;
        this.score = score;
        this.picture = image;
        this.date = date;
        this.comments = comments;
    }
    public PlayerCode(String hash, String name) {
        this.codeHash = hash;
        this.name = name;
    }

    public PlayerCode(String hash) {
        this.codeHash = hash;
    }


    public String getHashCode(){
        return this.codeHash;
    }



    public int getScore() {return this.score;}
    public String getName() {return this.name;}
    public String getPicture() {return this.picture;}

    public Date getDate() {return this.date;}

    public void setScore(int score) {
        this.score = score;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPicture(String picture) {this.picture = picture;}


    public ArrayList<String> getComments(){
        return this.comments;
    }

    public void addComment(String comment){
        comments.add(comment);
    }

    public void deleteComment(String comment){
        this.comments.remove(comment);
    }
}
