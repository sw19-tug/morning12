package com.twelve.morning.notebook;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

public class NoteURLTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void urlTest() {

        int rand = ThreadLocalRandom.current().nextInt(0, 1000000);

        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(typeText("Note " + rand), closeSoftKeyboard());
        onView(withId(R.id.et_note_body)).perform(typeText("Body https://gitignore.io"), closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());

        onView(withText("A" + rand)).perform(click());

        onView(withId(R.id.et_edit_note_body)).check(matches(hasTextColor(10)));
    }
}
