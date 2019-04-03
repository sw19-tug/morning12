package com.twelve.morning.notebook;

import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class NotePinTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testPinning() {
        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(typeText("A"), closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());

        Note[] notes = activityTestRule.getActivity().adapter.getNotes();
        Assert.assertTrue(notes[0].getPinned() == false);

        onView(withId(R.id.btn_pin)).perform(click());

        notes = activityTestRule.getActivity().adapter.getNotes();
        Assert.assertTrue(notes[0].getPinned() == true);
    }

    @Test
    public void testpinCheckbox(){
        String title_input = "dummyTitle";
        String body_input = "dummyBody";
        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.rl_create_note)).check(matches(isDisplayed()));
        onView(withId(R.id.et_note_title)).perform(clearText(), typeText(title_input));
        onView(withId(R.id.et_note_body)).perform(clearText(), typeText(body_input)).perform(closeSoftKeyboard());
        onView(withId(R.id.et_note_title)).check(matches(withText(title_input)));
        onView(withId(R.id.et_note_body)).check(matches(withText(body_input)));
        onView(withId(R.id.bt_note_create_save)).perform(click());
        onView(withId(R.id.bt_create)).check(matches(isDisplayed()));

        // -----------------------actual pin test --------------------------------

        onView(withId(R.id.cb_pinned)).check(matches(isDisplayed()));
        onView(withId(R.id.cb_pinned)).check(matches(isNotChecked()));

    }
}
