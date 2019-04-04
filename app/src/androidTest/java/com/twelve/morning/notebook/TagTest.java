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

public class TagTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void tagTest() {

        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(typeText("A"), closeSoftKeyboard());
        onView(withId(R.id.et_edit_note_body)).perform(typeText("Body #yoappsucsass"), closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());

        Note[] notes = activityTestRule.getActivity().adapter.getNotes();
        Assert.assertTrue(notes.length > 0);

        Note note = notes[0];
        Tag[] tags = note.getTags();

        Assert.assertTrue(tags.length > 0);
    }
}
