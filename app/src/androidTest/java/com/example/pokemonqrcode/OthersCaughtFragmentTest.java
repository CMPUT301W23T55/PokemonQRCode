package com.example.pokemonqrcode;

import static org.junit.Assert.assertFalse;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class OthersCaughtFragmentTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<ProfileActivity> rule =
            new ActivityTestRule<>(ProfileActivity.class, true, true);

    /**
     * Creates solo instance for all tests
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    // find a way to pass in the username so that the firestore thing doesnt initialize to null!!!!!!!!
    @Test
    public void testOthersCaughtFragment() {
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickInList(0);
        solo.waitForActivity(SelectCodeActivity.class);
        solo.assertCurrentActivity("Not in code view", SelectCodeActivity.class);
        solo.clickOnView(solo.getView(R.id.view_other_players_button));
        solo.waitForFragmentById(R.id.other_players_caught_fragment, 15000);
        //solo.scrollViewToSide();
        solo.clickOnView(solo.getView(R.id.close_others_button));
        assertFalse(solo.waitForFragmentById(R.id.other_players_caught_fragment));
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
