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

    /**
     * Gets the activity from the rule
     * Tests if activity starts
     * @throws Exception
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Tests opening the SelectCodeActivity from the ProfileActivity
     * @throws Exception
     */
    @Test
    public void openCodeView() throws Exception {
        solo.assertCurrentActivity("Not profile activity", ProfileActivity.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("Not in code view", SelectCodeActivity.class);
    }

    /**
     * Tests opening and viewing a list of other players who have found a code.
     * Opens the profile activity and selects a code.
     * Then opens the fragment to view the other players.
     * Closes the fragment.
     * @throws Exception
     */
    @Test
    public void viewOthersCaught() throws Exception {
        solo.assertCurrentActivity("Not in profile activity", ProfileActivity.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("not in select code", SelectCodeActivity.class);
        solo.clickOnView(solo.getView(R.id.view_other_players_button));
        solo.waitForFragmentById(R.id.other_players_caught_fragment, 3000);
        solo.clickOnView(solo.getView(R.id.close_others_button));
    }

    /**
     * Ends solo test after execution
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
