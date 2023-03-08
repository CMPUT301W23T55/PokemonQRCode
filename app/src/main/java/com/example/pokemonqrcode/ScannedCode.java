package com.example.pokemonqrcode;

import com.journeyapps.barcodescanner.ScanIntentResult;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class represents the result of a scanned code, will be used
 * to handle the methods responsible for scoring, name, image
 */
public class ScannedCode {

    private ScanIntentResult code;
    private byte[] hashedCode;
    private String hashAsString; //in case we need it
    private int score;
    private String name;
    private String picture;

    //this takes the result from the scanCode() method in MainActivity
    public ScannedCode(ScanIntentResult code) throws NoSuchAlgorithmException {
        this.code = code;
        this.hashedCode = this.hashScannedCode(code.getContents());
        this.hashAsString = toHexString(hashedCode);
    }
    /*
    https://www.geeksforgeeks.org/sha-256-hash-in-java/
    Retrieved March 4, 2023
    https://www.geeksforgeeks.org/copyright-information/
    Licensed under CCBY-SA
     */

    /**
     *
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

    public int calculateScore() {
        return 1;
    }

    /**
     * creates a name for the code according to the hash
     */
    public String createName() {
        return "test";
    }

    /**
     * creates a visual representation of the code
     */
    public String createImage() {
        return ":)";
    }

    public String getHashAsString() {
        return hashAsString;
    }
}
