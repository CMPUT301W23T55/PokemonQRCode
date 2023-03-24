package com.example.pokemonqrcode;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.widget.EditText;
import android.widget.TextView;

import android.app.Fragment;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.ActivityTestRule$$ExternalSyntheticLambda0;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginActivityTest {
    private Solo solo;


    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class, true, true);

    /**
     * Creates Solo instance for all tests
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Gets the activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Test case that checks for proper behaviour for the log in activity
     */
    @Test
    public void checkLogin() {
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);
        solo.clickOnView(solo.getView(R.id.submit_button));
        solo.waitForText("Ensure all fields have text", 1, 10000);
        EditText user = (EditText) solo.getView(R.id.edit_user_name_text);
        EditText pass = (EditText) solo.getView(R.id.edit_password_text);
        solo.enterText(user, "Hello:)");
        solo.enterText(pass,"World");
        solo.clickOnView(solo.getView(R.id.submit_button));
        solo.waitForText("Username/Password doesn't exist", 1, 10000);
        solo.clearEditText(user);
        solo.clearEditText(pass);
    }

    /**
     * Test that checks for proper behaviour from the register fragment
     */
    @Test
    public void checkRegisterFrag() {
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);
        solo.clickOnView(solo.getView(R.id.register_button));
        solo.waitForFragmentById(R.id.register_fragment, 15000);
        EditText new_user = (EditText) solo.getView(R.id.new_username_edit_text);
        EditText new_pass = (EditText) solo.getView(R.id.new_password_edit_text);
        EditText new_email = (EditText) solo.getView(R.id.new_email_edit_text);
        solo.enterText(new_user, "hellosolotest");
        solo.enterText(new_pass, "hellosolotest");
        solo.enterText(new_email, "hellosolo@test.com");
        solo.clearEditText(new_user);
        solo.clearEditText(new_pass);
        solo.clickOnView(solo.getView(R.id.cancel_register));
        assertFalse(solo.waitForFragmentById(R.id.register_fragment, 15000));
    }

    /**
     * closes activity
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
