package com.example.pokemonqrcode;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GithubActionsTest {

    @Test
    public void testIncrementMethod() {
        GithubPlaceholder test = new GithubPlaceholder();
        Assertions.assertEquals(0, test.test);
        test.increment();
        Assertions.assertEquals(1, test.test);

    }

}


