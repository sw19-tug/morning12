package com.twelve.morning.notebook;

import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class NoteSortingTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSortingButtonsAreDisplayed() {
        onView(withId(R.id.bt_sort_by_title)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_sort_by_creation)).check(matches(isDisplayed()));
    }

    @Test
    public void testSorting() {

        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(typeText("A"), closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());

        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(typeText("B"), closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());

        Note[] notes = activityTestRule.getActivity().adapter.getNotes();
        Assert.assertTrue(notes[0].getTitle().equals("B"));
        Assert.assertTrue(notes[1].getTitle().equals("A"));

        onView(withId(R.id.bt_sort_by_title)).perform(click());
        notes = activityTestRule.getActivity().adapter.getNotes();
        Assert.assertTrue(notes[0].getTitle().equals("A"));
        Assert.assertTrue(notes[1].getTitle().equals("B"));

        onView(withId(R.id.bt_sort_by_creation)).perform(click());
        notes = activityTestRule.getActivity().adapter.getNotes();
        Assert.assertTrue(notes[0].getTitle().equals("B"));
        Assert.assertTrue(notes[1].getTitle().equals("A"));

    }
}
