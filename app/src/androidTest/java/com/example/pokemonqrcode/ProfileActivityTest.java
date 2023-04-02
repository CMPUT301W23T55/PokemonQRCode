package com.example.pokemonqrcode;

<<<<<<< HEAD
import android.app.Instrumentation;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
=======
import android.app.Activity;
>>>>>>> main

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

<<<<<<< HEAD
import org.junit.After;
=======
>>>>>>> main
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ProfileActivityTest {
<<<<<<< HEAD
    private Solo solo;

    @Rule
    public ActivityTestRule<ProfileActivity> rule =
            new ActivityTestRule<>(ProfileActivity.class, true, true);

    /**
     * Sets up solo tests for the profile activity
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Executes UI tests on the Profile activity.
     * Selects a code in the profile activity and views it in the select code activity.
     * Returns to the Profile activity and selects the sorting spinner to ensure that
     * it works.
     */
    @Test
    public void testProfileActivity() {
        solo.assertCurrentActivity("Wrong activity", ProfileActivity.class);
        solo.clickInList(1);
        solo.waitForActivity(SelectCodeActivity.class);
        solo.assertCurrentActivity("Not in SelectCodeActivity", SelectCodeActivity.class);
        solo.clickOnView(solo.getView(R.id.home_btn));
        solo.assertCurrentActivity("Not in profile activity", ProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.sorting_spinner));
        View spinnerView = solo.getView(R.id.sorting_spinner);
        solo.clickOnView(spinnerView);
        solo.scrollToTop();
        solo.clickOnView(solo.getView(TextView.class, 1));
        solo.sleep(5000);
        //solo.scrollToTop();
        //solo.clickOnView(solo.getView(TextView.class, 2));
        //solo.sleep(5000);
        //solo.clickOnView(spinnerView);
        //solo.isSpinnerTextSelected(0, "Score: Low -> High");
        //solo.scrollToTop();
        //solo.clickOnView(solo.getView(TextView.class, 2));
        //solo.sleep(5000);
        //solo.pressSpinnerItem(R.id.sorting_spinner, -1);
        //solo.pressSpinnerItem(R.id.sorting_spinner, -1);
    }

    /**
     * Takes down the UI tests
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
=======

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


>>>>>>> main
}
