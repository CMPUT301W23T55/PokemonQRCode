package com.example.pokemonqrcode;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.StringContains.containsString;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class test for ProfileActivity using intent testing
 */
public class ProfileActivityTest {

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
    /*
    test for listview item click
     */

    @Test
    public void testItemClick(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.profile_btn));
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.code_list));
        solo.assertCurrentActivity("Wrong Activity", SelectCodeActivity.class);
    }

    /*
    tests spinner for sorting: low -> high
     */
    @Test
    public void testSpinnerLowToHigh(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.profile_btn));
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        onView(withId(R.id.sorting_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.sorting_spinner)).check(matches(withSpinnerText(containsString("Score: Low -> High"))));
    }

    /*
    tests spinner for sorting: high -> low
     */
    @Test
    public void testSpinnerHighToLow(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.profile_btn));
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        onView(withId(R.id.sorting_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.sorting_spinner)).check(matches(withSpinnerText(containsString("Score: High -> Low"))));
    }
    /*
    tests spinner for sorting: date
     */
    @Test
    public void testSpinnerDate(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.profile_btn));
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        onView(withId(R.id.sorting_spinner)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.sorting_spinner)).check(matches(withSpinnerText(containsString("Date"))));
    }

    /*
    tests if view is visible
     */
    @Test
    public void testIsViewVisible(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.profile_btn));
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        onView(withId(R.id.code_list)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
