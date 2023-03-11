package com.example.pokemonqrcode;

import com.journeyapps.barcodescanner.ScanIntentResult;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.*;

/**
 * This class represents the result of a scanned code, will be used
 * to handle the methods responsible for scoring, name, image
 */
public class ScannedCode {

    private ScanIntentResult code;
    private byte[] hashedCode;
    private String hashAsString;
    private int score;
    private String name;
    private String picture;

    //this takes the result from the scanCode() method in MainActivity
    public ScannedCode(ScanIntentResult code) throws NoSuchAlgorithmException {
        this.code = code;
        this.hashedCode = this.hashScannedCode(code.getContents());
        this.hashAsString = toHexString(hashedCode);
        this.calculateScore();
        this.createName();
        this.createImage();
    }
    /*
    https://www.geeksforgeeks.org/sha-256-hash-in-java/
    Retrieved March 4, 2023
    https://www.geeksforgeeks.org/copyright-information/
    Licensed under CCBY-SA
     */

    /**
     * @param input
     * String of the contents of the scanned code
     * @return
     * a byte array of the SHA-256 hash of the string
     * @throws NoSuchAlgorithmException
     */
    private static byte[] hashScannedCode(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    /**
     *
     * @param hash
     * SHA-256 hash of the code
     * @return
     * String representation of the hash
     */
    private static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    /**
     * calculates the score according to the hash (use eClass rules)
     */


    public void calculateScore() {
        // Currently calculate score does not use the eClass rules
        // just sums all of the byte values
        int total = 0;
        for (int i = 0; i < hashedCode.length; ++i) {
            if (hashedCode[i] < 0) {
                hashedCode[i] = (byte) -hashedCode[i];
            }
            total += hashedCode[i];
        }
        this.setScore(total);
    }

    /**
     * Sets the score of the code
     * @param score
     */
    public void setScore(int score) {
        this.score = score;

    }

    /**
     * gets the score of the code
     * @return integer
     */

    public int getScore() {return this.score;}

    /**
     * Creates a name for the code according to the hashedCode
     * @return
     * A string containing the name of the scanned code
     */
    public void createName() {
        String[] typeOptions = {"Normal", "Grass", "Water", "Fire", "Ground", "Electric",
                "Psychic", "Poison", "Bug", "Rock", "Steel", "Ice", "Ghost", "Flying", "Fighting", "Dragon"};
        String[] modifiers = {"Excellent", "Exceptional", "Favorable", "Great",
                "Marvelous", "Superb", "Valuable", "Wonderful", "Super", "Superior", "Honorable",
                "Gnarly", "Admirable", "Awesome", "Amazing", "Magnificent"};
        String[] prefixes = {"Fur", "Electa", "Cater", "Gol", "Kra", "Mag", "Nido", "Para",
                "Regi", "Sala", "War", "Zig", "Abra", "Bul", "Deer", "Ivy"};
        String[] suffixes = {"chu", "vee", "zard", "chomp", "ario", "too", "ape", "puff",
                "bear", "fly", "tales", "buto", "ler", "ray", "champ", "bat"};
        String space = " ";
        int max = 16;
        String type = typeOptions[hashedCode[0] % max];
        String modifier = modifiers[hashedCode[1] % max];
        String prefix = prefixes[hashedCode[2] % max];
        String suffix = suffixes[hashedCode[3] % max];
        String finalName = new StringBuilder()
                .append(modifier)
                .append(space)
                .append(type)
                .append(space)
                .append(prefix)
                .append(suffix)
                .toString();
        this.setName(finalName);

    }

    /**
     * creates a visual representation of the code
     * @return
     * A string that is an "image" of the scanned QR Code
     */

    public void createImage() {
        String headOptions[] = {" .     _,\n" ,
                "                   |`\\__/ /\n" ,
                "                   \\  . .(\n" ,
                "                    | __T|\n" ,
                "                   /   |"};
        String bodyOptions[] = { "/(______);\n" ,
                "  (         (\n" ,
                "   |:------( )"};
        String legOptions[] = {""};
        int max = 3;
        String head = headOptions[hashedCode[4] % max];
        String body = bodyOptions[hashedCode[5] % max];
        String legs = legOptions[hashedCode[6] % max];
        String finalImage = new StringBuilder()
                .append(head)
                .append(body)
                .append(legs)
                .toString();
        this.setPicture(finalImage);
    }

    /**
     * returns the name of the code
     * @return
     * String name of code
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the code
     * @param name - string of the code's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns generated "image" of scanned code
     * @return
     * String representing image of code
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Sets the picture of the code
     * @param picture
     */
    public void setPicture(String picture) {
        this.picture = picture;

    }

    /**
     * Gets a string representing the hash of the code
     * @return String of the hash
     */
    public String getHashAsString() {
        return hashAsString;
    }

    /**
     * Gets byte array representing the scanned code
     * @return Byte Array
     */
    public byte[] getHashedCode() {
        return hashedCode;
    }
}

