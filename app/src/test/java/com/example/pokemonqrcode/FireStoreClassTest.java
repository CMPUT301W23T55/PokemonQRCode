package com.example.pokemonqrcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import android.widget.Button;
import android.widget.Toast;

import org.junit.jupiter.api.Test;

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
