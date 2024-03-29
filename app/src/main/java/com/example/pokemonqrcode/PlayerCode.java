package com.example.pokemonqrcode;


import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * This class is what shows up on the player profile
 * Create custom ListView adapter similar to GasStation in Assignment1
 */

public class PlayerCode implements Serializable {
    private String HashCode;
    private int Score;
    private String Name;
    private Date Date;
    private String Picture;
    private Bitmap photo;
    private Locations location;

    private ArrayList<String> Comments = new ArrayList<String>();
    //private String Comments;
    private boolean imgExists;

    LocationManager locationManager;


    public PlayerCode(Locations location, String name, int score) {
        this.Name = name;
        this.location = location;
        this.Score = score;

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
                      Date date, String comment) {
        this.HashCode = hash;
        this.Name = name;
        this.Score = score;
        this.Picture = image;
        this.Date = date;
        this.Comments.add(comment);
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

    public Bitmap getPhoto() {
        return photo;
    }

    public Locations getLocation() {
        return this.location;

    }
    public boolean getImgExists() { return imgExists;}

    public ArrayList<String> getComments(){
        return this.Comments;
    }

    public void setScore(int score) {
        this.Score = score;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public void setPicture(String picture) {this.Picture = picture;}
    public void setDate(Date date) {this.Date = date;}
    public void setPhoto(Bitmap photo) {this.photo = photo;}

    public void setImgExists(boolean imgExists) {this.imgExists = imgExists;}


    public void setLocation(Locations Location) {
        this.location = Location;
    }
    public void addComment(String comment){
        this.Comments.add(comment);
    }

    public void deleteComment(String comment){
        this.Comments.remove(comment);
    }

    @NonNull
    @Override
    public String toString() {
        return this.getName()+"\n"+this.Score+"\n"+this.Date;
    }

    public static Comparator<PlayerCode> PlayerScoreComparator = new Comparator<PlayerCode>() {
        @Override
        public int compare(PlayerCode playerCode, PlayerCode t1) {
            return t1.getScore() - playerCode.getScore();
        }
    };

    public static Comparator<PlayerCode> PlayerDateComparator = new Comparator<PlayerCode>() {
        @Override
        public int compare(PlayerCode playerCode, PlayerCode t1) {
            if(playerCode.getDate().after(t1.getDate())){
                return -1;
            } else if (playerCode.getDate().equals(t1.getDate())){
                return 0;
            }
            return 1;
        }
    };

//    public IGeoPoint getGeolocation() {
//        IGeoPoint l = null;
//        return l;
//    }
}


