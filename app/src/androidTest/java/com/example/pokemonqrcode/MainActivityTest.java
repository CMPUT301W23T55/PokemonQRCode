package com.example.pokemonqrcode;

import android.app.Activity;
import android.widget.Button;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

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

    @Test
    public void checkProfileButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.profile_btn));
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);

        solo.clickOnView(solo.getView(R.id.home_btn));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }
    //how do i test the camera with junit :(

}
