package com.example.pokemonqrcode;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ProfileActivityTest {
<<<<<<< HEAD
=======

>>>>>>> 72fae0f0a2e1aec6df55378bcd9e85be9e58d3dd
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    @Test
    public void start(){
        Activity activity = rule.getActivity();
    }


}
