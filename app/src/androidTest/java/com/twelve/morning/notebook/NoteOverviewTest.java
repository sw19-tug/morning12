package com.twelve.morning.notebook;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.Matchers.anything;

public class NoteOverviewTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAddButtonIsDisplayed() {
        onView(withId(R.id.bt_create)).check(matches(isDisplayed()));
    }

    @Test
    public void testNotesListViewIsDisplayed() {
        onView(withId(R.id.list_notes)).check(matches(isDisplayed()));
    }

    @Test
    public void testNotesListViewContainsElement() {
        onView(allOf(anything(), withParent(withId(R.id.list_notes)))).check(matches(isDisplayed()));
    }
}
