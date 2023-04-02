package com.example.pokemonqrcode;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Activity;
import android.view.KeyEvent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class tests SearchUserActivity using intent testing
 * @author jawad
 */
public class SearchUserActivityTest {

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

    /**
     * Checks the functionality of find user and return to home buttons
     */
    @Test
    public void checkHomeButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
//        solo.clickOnButton(0);
        solo.clickOnView(solo.getView(R.id.find_users));
        solo.assertCurrentActivity("Wrong Activity", SearchUserActivity.class);
        solo.clickOnView(solo.getView(R.id.return_home));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    /**
     * tests searching users by username
     */
    @Test
    public void testUserSearch() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.find_users));
        solo.assertCurrentActivity("Wrong Activity", SearchUserActivity.class);
        onView(withId(R.id.search_view)).perform(click());  //open the searchView
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("Jawad"));// the text was input
        onView(withId(R.id.search_view)).perform(pressKey(KeyEvent.KEYCODE_ENTER));  // starting the object  search
    }

    /*
    tests for the visibility of activity during launch
     */
    @Test
    public void testIsViewVisibleOnLaunch(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.find_users));
        solo.assertCurrentActivity("Wrong Activity", SearchUserActivity.class);
        onView(withId(R.id.rec_view)).check(matches(isDisplayed()));
    }

    /*
    tests cardview when clicked
     */
    @Test
    public void testSelectCardView() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.find_users));
        solo.assertCurrentActivity("Wrong Activity", SearchUserActivity.class);
        onView(withId(R.id.rec_view)).perform(click());
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }


    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }


}
