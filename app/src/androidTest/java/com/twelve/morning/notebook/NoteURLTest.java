package com.twelve.morning.notebook;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ThreadLocalRandom;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

public class NoteURLTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void urlTest() {

        double rand = Math.random();

        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(typeText("Note " + rand), closeSoftKeyboard());
        onView(withId(R.id.et_note_body)).perform(typeText("Body https://gitignore.io lelelelele hiho"), closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());
        onView(withText("Note " + rand)).perform(click());

        onView(withText("Body https://gitignore.io lelelelele hiho")).check(matches(isDisplayed()));

        Espresso.openContextualActionModeOverflowMenu();
        onView(withText(R.string.delete_note)).perform(click());
    }
}
