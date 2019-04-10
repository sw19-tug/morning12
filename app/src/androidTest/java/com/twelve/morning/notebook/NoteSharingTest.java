package com.twelve.morning.notebook;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class NoteSharingTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void menuButtonOverviewTest() {
        Espresso.openContextualActionModeOverflowMenu();
        onView(withText(R.string.import_bt)).check(matches(isDisplayed()));
        onView(withText(R.string.import_bt)).perform(click());
        onView(withText(R.string.import_bt)).check(matches(doesNotExist()));
    }
}
