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
    @Test
    public void checkSearchButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.find_users));
        solo.assertCurrentActivity("Wrong Activity", SearchUserActivity.class);

        solo.clickOnView(solo.getView(R.id.return_home));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }
    @Test
    public void checkMapButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.location_btn));
        solo.assertCurrentActivity("Wrong Activity", MapActivity.class);

        solo.clickOnView(solo.getView(R.id.back_button_head));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }
    @Test
    public void checkLeaderboardButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.leaderboards));
        solo.assertCurrentActivity("Wrong Activity", LeaderboardActivity.class);

        solo.clickOnView(solo.getView(R.id.return_home));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

}
