package com.twelve.morning.notebook;


import android.Manifest;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class TextToSpeechTest {

    @Rule
    public ActivityTestRule<MainActivity> activityMainTestRule =
            new ActivityTestRule<>(MainActivity.class);

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
    public void checkSpeechButton() {
        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(typeText("Hello World"), closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());

        onView(withText("Hello World")).perform(click());
        Espresso.openContextualActionModeOverflowMenu();

        onView(withText(R.string.text_to_speech)).check(matches(isDisplayed()));
        onView(withText(R.string.text_to_speech)).perform(click());
    }
}