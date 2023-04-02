package com.example.pokemonqrcode;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;
import android.provider.ContactsContract;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SelectCodeActivityTest {
    private Solo solo;

    // Starts tests from Profile activity to ensure that the database correctly instantiates
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
     * Gets activity from rule
     * Tests if activity starts
     * @throws Exception
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Tests if it is possible to navigate from the ProfileActivity to the SelectCodeActivity
     * @throws Exception
     */
    @Test
    public void navigateToSelectCodeActivity() throws Exception{
        solo.assertCurrentActivity("Not in profile activity", ProfileActivity.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("Not in selectCode activity", SelectCodeActivity.class);
    }

    /**
     * Tests if it is possible to navigate to the SelectCodeActivity and back to the ProfileActivity
     * @throws Exception
     */
    @Test
    public void selectCode() throws Exception {
        solo.assertCurrentActivity("Not in the profile activity", ProfileActivity.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("Not in select code activity", SelectCodeActivity.class);
        solo.clickOnView(solo.getView(R.id.return_to_profile_btn));
        solo.clickInList(1);
    }

    /**
     * Tests if a view of other players who have found the same code can be viewed
     * @throws Exception
     */
    @Test
    public void viewOtherPlayers() throws Exception {
        solo.assertCurrentActivity("Not in ProfileActivity", ProfileActivity.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("Not in SelectCode activity", SelectCodeActivity.class);
        solo.clickOnView(solo.getView(R.id.view_other_players_button));
        solo.waitForFragmentById(R.id.other_players_caught_fragment, 3000);
        solo.clickOnView(solo.getView(R.id.close_others_button));
    }

    /**
     * Tests the comment functionality of the select code activity
     * @throws Exception
     */
    @Test
    public void commentTest() throws Exception {
        solo.assertCurrentActivity("Not in ProfileActivity", ProfileActivity.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("Not in SelectCode activity", SelectCodeActivity.class);
        EditText commentText = (EditText) solo.getView(R.id.comments);
        solo.enterText(commentText, "Test Comment");
        solo.clearEditText(commentText);
        solo.clickOnView(solo.getView(R.id.save_comment_btn));
        solo.waitForText("Comment saved!");
    }

    /**
     * Tests the delete functionality of the select code activity
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        solo.assertCurrentActivity("Not in ProfileActivity", ProfileActivity.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("Not in SelectCode activity", SelectCodeActivity.class);
        solo.clickOnView(solo.getView(R.id.delete_btn));
        onView(withText("Delete?")).inRoot(isDialog()).check(matches(isDisplayed()));
    }
}
