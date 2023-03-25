package com.example.pokemonqrcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import java.util.ArrayList;

public class FireStoreClassTest {
    @Test
    public void testAdd(){
        FireStoreClass f = new FireStoreClass("Kyle");

        PlayerCode pc = new PlayerCode("123456","Charizard", 122,
                "Picture of Charizard");
        f.addAQRCode(pc);

        ArrayList<PlayerCode> codes = f.getPlayerCodes();
        assertEquals(codes.size(),1);
    }
}
//how to get comments
/*
            ArrayList<PlayerCode> pcs = f.getPlayerCodes();
            ArrayList<String> j = pcs.get(0).getComments();
            String i = j.get(0);
 */