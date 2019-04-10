package com.twelve.morning.notebook;

import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class NoteTaggingTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void tagTest() {

        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(typeText("A"), closeSoftKeyboard());
        onView(withId(R.id.et_note_body)).perform(typeText("Body #yoappsucsass #heylookatmeimahashtag #thursdaycommits #livinglife #justandroidappthings"), closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());

        Note[] notes = activityTestRule.getActivity().adapter.getNotes();
        Assert.assertTrue(notes.length > 0);

        Note note = notes[0];
        List<Tag> tags = note.getTags();

        Assert.assertTrue(tags.size() > 0);

        Tag t1 = tags.get(0);
        Assert.assertTrue(t1.getContent().equals("#yoappsucsass"));

    }

    @Test
    public void testPinning() {
        String[] notes = TagManager.parse("#tag1 #tag2");
        Assert.assertTrue(notes[0].equals("tag1") && notes[1].equals("tag2"));
    }
}