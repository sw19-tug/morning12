package com.twelve.morning.notebook;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class NoteDeleteTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testDeletion() {

        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(typeText("ZZZZ"), closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());

        onView(withText("ZZZZ")).perform(click());
        Espresso.openContextualActionModeOverflowMenu();

        onView(withText(R.string.delete_note)).perform(click());

        onView(withText("ZZZZ")).check(doesNotExist());
    }
}
