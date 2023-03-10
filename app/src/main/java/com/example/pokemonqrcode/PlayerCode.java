package com.example.pokemonqrcode;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class is what shows up on the player profile
 * Create custom ListView adapter similar to GasStation in Assignment1
 */

public class PlayerCode {
    /*
    name
    score
    date
    hashcode
    picture
    geolocation
    comment
     */
    private String codeHash;
    private int score;
    private String name;
    private Date date;
    private String picture;
    private ArrayList<String> comments;

    public PlayerCode(String hash, String name, int score, String image) {
        this.codeHash = hash;
        this.name = name;
        this.score = score;
        this.picture = image;
        this.date = new Date();
    }

    public PlayerCode(String hash, String name) {
        this.codeHash = hash;
        this.name = name;
    }

    public PlayerCode(String hash) {
        this.codeHash = hash;
    }

    public PlayerCode() {

    }

    public int getScore() {return this.score;}
    public String getName() {return this.name;}
    public String getPicture() {return this.picture;}

    public void setScore(int score) {
        this.score = score;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPicture(String picture) {this.picture = picture;}

}
