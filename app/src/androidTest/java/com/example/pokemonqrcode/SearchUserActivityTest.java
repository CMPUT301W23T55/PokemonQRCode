package com.example.pokemonqrcode;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
     *
     */
    @Test
//    public void searchUser() {
//        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
//        solo.clickOnView(solo.getView(R.id.find_users));
//        solo.assertCurrentActivity("Wrong Activity", SearchUserActivity.class);
//        solo.clickOnView(solo.getView(R.id.search_view));
//        solo.enterText((EditText) solo.getView(R.id.search_view),"Jawad");
////        int resID = Resources.getSystem().getIdentifier("search_src_text",
////                "id", "android");
////        Log.d("herrrrrrrrrr", "searchUser: "+resID);
////        SearchUserActivity activity = (SearchUserActivity) solo.getCurrentActivity();
////        ArrayList<Users> filterList = (ArrayList<Users>) activity.filteredList;
////        SearchView searchView = (SearchView) solo.getView("search_view");
////        Looper.prepare();
////        searchView.setQuery("Jawad",true);
////        solo.getView(R.id.rec_view);
//////        solo.enterText((EditText) solo.getView(R.id.search_view));
//////        solo.enterText(Resources.getSystem().getIdentifier("search_view",
//////                "id", "android"), "Jawad");
////        Log.d("herrrrrrrrrr", "searchUser: "+filterList.size());
////        assertEquals(filterList.size(),1);
////        Looper.loop();
//
//    }
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }


}
