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
    public ActivityTestRule<CreateNoteActivity> activityCreateNoteTestRule =
            new ActivityTestRule<>(CreateNoteActivity.class);


    @Test
    public void checkVisibleButton() {
        onView(withId(R.id.export_button)).check(matches(isDisplayed()));

    }

    @Test
    public void checkCklickableButton() {
        onView(withId(R.id.export_button)).perform(click());


    }

    @Test
    public void checkCopress() {
        onView(withId(R.id.export_button)).check(matches(isDisplayed()));
    }
}