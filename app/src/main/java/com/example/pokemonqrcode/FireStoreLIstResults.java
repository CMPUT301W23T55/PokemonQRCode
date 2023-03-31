package com.example.pokemonqrcode;

import java.util.ArrayList;

/**
 * Interface used to get a List of player codes from a firestore query (used to deal with Firestore's
 * asynchronous behaviour)
 */
public interface FireStoreLIstResults {

    void onResultGetList(ArrayList<PlayerCode> playerCodeList);
}
