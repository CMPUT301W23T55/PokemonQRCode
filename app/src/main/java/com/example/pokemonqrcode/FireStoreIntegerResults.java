package com.example.pokemonqrcode;

/**
 * Interface used to get the total score and total scanned QR Codes
 * from a firestore query (used to deal with Firestore's
 * asynchronous behaviour)
 */
public interface FireStoreIntegerResults {
    public void onResultGetInt(int result, int count);
}
