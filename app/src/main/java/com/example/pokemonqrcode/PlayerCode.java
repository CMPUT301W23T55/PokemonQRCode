package com.example.pokemonqrcode;


import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;


import androidx.annotation.NonNull;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

/**
 * This class is what shows up on the player profile
 * Create custom ListView adapter similar to GasStation in Assignment1
 */

public class PlayerCode {
    private String HashCode;
    private int Score;
    private String Name;
    private Date Date;
    private String Picture;
    private Bitmap photo;
    private List<Address> location;
    private String Comments;


    public PlayerCode(ScannedCode code, Bitmap photo, List<Address> location) {
        this.Name = code.getName();
        this.Score = code.getScore();
        this.Picture = code.getPicture();
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
        this.HashCode = hash;
        this.Name = name;
        this.Score = score;
        this.Picture = image;
        this.Date = new Date();
    }

    public PlayerCode(String hash, String name, int score, String image, Date date) {
        this.HashCode = hash;
        this.Name = name;
        this.Score = score;
        this.Picture = image;
        this.Date = date;
    }

    public PlayerCode(String hash, String name, int score, String image,
                      Date date, String comments) {
        this.HashCode = hash;
        this.Name = name;
        this.Score = score;
        this.Picture = image;
        this.Date = date;
        this.Comments = comments;
    }

    public PlayerCode() {
//
    }

    public PlayerCode(String hash, String name) {
        this.HashCode = hash;
        this.Name = name;
    }

    public PlayerCode(String hash) {
        this.HashCode = hash;
    }


    public String getHashCode(){
        return this.HashCode;
    }



    public int getScore() {return this.Score;}
    public String getName() {return this.Name;}
    public String getPicture() {return this.Picture;}

    public Date getDate() {return this.Date;}

    public void setScore(int score) {
        this.Score = score;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public void setPicture(String picture) {this.Picture = picture;}
    public void setDate(Date date) {this.Date = date;}


    public String getComments(){
        return this.Comments;
    }

//    public void addComment(String comment){
//        Comments.add(comment);
//    }

//    public void deleteComment(String comment){
//        this.Comments.remove(comment);
//    }

    @NonNull
    @Override
    public String toString() {
        return this.getName()+"\n"+this.Score+"\n"+this.Date;
    }
}
