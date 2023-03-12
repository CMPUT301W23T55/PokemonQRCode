package com.example.pokemonqrcode;

import android.app.Activity;
import android.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
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

    @Test
    public void checkLogin() {
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);

        solo.clickOnView(solo.getView(R.id.submit_button));
        solo.waitForText("Ensure fields are filled in");

        EditText user = (EditText) solo.getView(R.id.edit_user_name_text);
        EditText pass = (EditText) solo.getView(R.id.edit_password_text);
        solo.enterText(user, "Hello:)");
        solo.clearEditText(user);
        solo.enterText(pass,"World");
        solo.clearEditText(pass);
    }

    @Test
    public void checkRegisterFrag() {
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);
        solo.clickOnView(solo.getView(R.id.register_button));
        
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
