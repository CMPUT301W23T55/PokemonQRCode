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
    private int hashcode;

    //may be subject to change
    private String picture;
    private Pair<Integer, Integer> geolocation;

    private ArrayList<String> comments;


    public PlayerCode(){

    }
    public PlayerCode(String name, int score, Date date, int hashcode,
                      String picture, Pair<Integer, Integer> geolocation, ArrayList<String> comments){
        this.name = name;
        this.score = score;
        this.date = date;
        this.hashcode = hashcode;
        this.picture = picture;
        this.geolocation = geolocation;
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

    public int getPlayerCodeHashCode(){
        return this.hashcode;
    }

    public String getPlayerCodePicture(){
        return this.picture;
    }

    public Pair<Integer, Integer> getPlayerCodeLocation(){
        return this.geolocation;
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
