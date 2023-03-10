package com.example.pokemonqrcode;

import android.util.Pair;

import java.util.ArrayDeque;
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
    private String name;
    private int score;
    private Date date;
    private String hashcode;

    //may be subject to change
    private String picture;
    private Pair<Integer, Integer> geolocation;

    private ArrayList<String> comments;


    public PlayerCode(){

    }
    public PlayerCode(String name, int score, Date date, String hashcode,
                      String picture, ArrayList<String> comments){
        this.name = name;
        this.score = score;
        this.date = date;
        this.hashcode = hashcode;
        this.picture = picture;
        this.comments = comments;
    }

    public String getPlayerCodeName(){
        return this.name;
    }

    public int getPlayerCodeScore(){
        return this.score;
    }

    public Date getPlayerCodeDate(){
        return this.date;
    }

    public String getPlayerCodeHashCode(){
        return this.hashcode;
    }

    public String getPlayerCodePicture(){
        return this.picture;
    }


    public ArrayList<String> getPlayerCodeComments(){
        return this.comments;
    }

    public void addComment(String comment){
        this.comments.add(comment);
    }

    public void deleteComment(String comment){
        this.comments.remove(comment);
    }
}
