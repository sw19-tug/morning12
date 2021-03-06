package com.twelve.morning.notebook;

import android.Manifest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class MainActivitySearchTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION);

    @Before
    public void resetDatabase(){
        DatabaseWrapper.getInstance().reset();
    }

    @Test
    public void searchBarTest() {
        onView(withId(R.id.search_view_find_text)).check(matches(isDisplayed()));
    }

    @Test
    public void searchTagTest() {
        String title_input = "a title";
        String body_input = "#tag1 #tag2 #anothertag";

        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(clearText(), typeText(title_input))
                .perform(closeSoftKeyboard());
        onView(withId(R.id.et_note_body)).perform(clearText(), typeText(body_input))
                .perform(closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());

        onView(withId(R.id.search_view_find_text)).perform(click());
        onView(withId(R.id.search_view_find_text)).perform(typeText("#anothertag"))
                .perform(closeSoftKeyboard());

        onView(withText(title_input)).check(matches(isDisplayed()));
    }

}
