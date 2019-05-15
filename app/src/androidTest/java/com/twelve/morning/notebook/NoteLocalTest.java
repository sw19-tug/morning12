package com.twelve.morning.notebook;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class NoteLocalTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkVButton () {
        Espresso.openContextualActionModeOverflowMenu();
        onView(withText(R.string.local_string)).check(matches(isDisplayed()));
    }

    @Test
    public void openChangeLanguagePopup () {
        Espresso.openContextualActionModeOverflowMenu();
        onView(withText(R.string.local_string)).check(matches(isDisplayed()));
        onView(withText(R.string.local_string)).perform(click());

        onView(withText("Select Language")).check(matches(isDisplayed()));
    }
}