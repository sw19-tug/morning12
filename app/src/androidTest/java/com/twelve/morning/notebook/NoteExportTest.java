package com.twelve.morning.notebook;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class NoteExportTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkVisibleButton () {
        onView(withId(R.id.bt_export)).check(matches(isDisplayed()));
    }

    @Test
    public void checkCklickableButton () {
        onView(withId(R.id.bt_export)).perform(click());
    }

    @Test
<<<<<<< HEAD
    public void checkCopress() {
        onView(withId(R.id.export_button)).check(matches(isDisplayed()));
=======
    public void checkCompress() {

>>>>>>> 7b9ff6636f45b09f9f9967dfda175e6231b0dcbf
    }
}