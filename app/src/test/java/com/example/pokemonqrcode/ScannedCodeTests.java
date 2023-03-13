package com.example.pokemonqrcode;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.Test;


import java.security.NoSuchAlgorithmException;

public class ScannedCodeTests {

    @Test
    public void testNameGeneration() throws NoSuchAlgorithmException {
        byte[] array = {1, -128, 43, -50, 32, 127, 120};
        ScannedCode scan = new ScannedCode(array);
        scan.createName();
        System.out.println(scan.getName());
        assertNotEquals(null, scan.getName());
    }
    @Test
    public void testImageGeneration() throws NoSuchAlgorithmException {
        byte[] array = {-2, 45, -128, 127, -60, 20, 126};
        ScannedCode scan = new ScannedCode(array);
        scan.createImage();
        System.out.println(scan.getPicture());
        assertNotEquals(null, scan.getPicture());
    }
}
