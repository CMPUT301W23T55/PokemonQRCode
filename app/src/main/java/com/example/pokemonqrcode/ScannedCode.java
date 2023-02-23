package com.example.pokemonqrcode;

import com.journeyapps.barcodescanner.ScanIntentResult;

/**
 * This class represents the result of a scanned code, will be used
 * to handle the methods responsible for scoring, name, image
 */
public class ScannedCode {

    private ScanIntentResult code;
    private String hashedCode;
    private int score;
    private String name;
    private String picture;

    //this takes the result from the scanCode() method in MainActivity
    public ScannedCode(ScanIntentResult code) {
        this.code = code;
        hashedCode = this.hashScannedCode();

    }

    /**
     * Will hash the code with SHA-256 hashing
     * @return
     * a string representation of the hash
     */
    private String hashScannedCode() {
        return "test";
    }

    /**
     * calculates the score according to the hash (use eClass rules)
     */
    public void calculateScore() {

    }

    /**
     * creates a name for the code according to the hash
     */
    public void createName() {

    }

    /**
     * creates a visual representation of the code
     */
    public void createImage() {

    }


}
