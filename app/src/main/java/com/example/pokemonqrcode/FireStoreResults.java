package com.example.pokemonqrcode;

/**
 * Interface used to get a boolean result from a firestore query (used to deal with Firestore's
 * asynchronous behaviour)
 */
public interface FireStoreResults {
    public void onResultGet(boolean result);
}

