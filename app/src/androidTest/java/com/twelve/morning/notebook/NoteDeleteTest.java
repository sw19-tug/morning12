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
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class NoteDeleteTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testDeletion() {

        // create note
        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(typeText("ZZZZ"), closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());

        // open created note
        onView(withText("ZZZZ")).perform(click());

        // delete note
        onView(withId(R.id.bt_edit_delete_note)).perform(click());

        // check if deleted
        onView(withText("ZZZZ")).check(doesNotExist());

    }
}
