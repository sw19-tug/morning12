package com.twelve.morning.notebook;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class NoteSortingTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAddButtonIsDisplayed() {
        onView(withId(R.id.bt_sort_by_title)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_sort_by_creation)).check(matches(isDisplayed()));
    }
}
