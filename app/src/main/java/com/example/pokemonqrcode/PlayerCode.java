package com.example.pokemonqrcode;

import android.graphics.Bitmap;
import android.location.Location;

import java.util.Date;

/**
 * This class is what shows up on the player profile
 * Create custom ListView adapter similar to GasStation in Assignment1
 */

public class PlayerCode {
    private String name;
    private int score;
    private String image;
    private Bitmap photo;
    private Location location;

    public PlayerCode(String name, int score, String image, Bitmap photo, Location location) {
        this.name = name;
        this.score = score;
        this.image = image;
        this.photo = photo;
        this.location = location;
    }

    /*

    name
    score
    date
    picture
    geolocation
    comment

     */
}
