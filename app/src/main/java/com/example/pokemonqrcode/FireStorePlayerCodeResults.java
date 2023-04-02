package com.example.pokemonqrcode;

/**
 * Interface used to get a Player code from a firestore query (used to deal with Firestore's
 * asynchronous behaviour)
 */
public interface FireStorePlayerCodeResults {
    void onResultGetPlayerCode(PlayerCode pCode);

}
