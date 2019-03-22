package com.twelve.morning.notebook;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class CreateNoteActivityTest {

    @Rule
    public ActivityTestRule<CreateNoteActivity> activityCreateNoteTestRule =
            new ActivityTestRule<>(CreateNoteActivity.class);


    @Test
    public void popUpContainsFieldsAndButtons(){
        onView(withId(R.id.rl_create_note)).check(matches(isDisplayed()));
        onView(withId(R.id.et_note_title)).check(matches(isDisplayed()));
        onView(withId(R.id.et_note_body)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_note_create_save)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_note_create_cancel)).check(matches(isDisplayed()));
    }

    @Test
    public void testInputFields() {
        String title_input = "testTitle";
        String body_input = "testBody";
        EditText note_title = activityCreateNoteTestRule.getActivity().findViewById(R.id.et_note_title);
        assertNotNull(note_title);
        onView(withId(R.id.et_note_title)).perform(clearText(), typeText(title_input));
        onView(withId(R.id.et_note_body)).perform(clearText(), typeText(body_input));
        onView(withId(R.id.et_note_title)).check(matches(withText(title_input)));
        onView(withId(R.id.et_note_body)).check(matches(withText(body_input)));
    }

    @Test
    public void returnToMainActivityByAbort(){
        onView(withId(R.id.rl_create_note)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_note_create_cancel)).perform(click());
        onView(withId(R.id.bt_create)).check(matches(isDisplayed()));
    }

    @Test
    public void returnToMainActivityBySave(){
        onView(withId(R.id.rl_create_note)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_note_create_save)).perform(click());
        onView(withId(R.id.bt_create)).check(matches(isDisplayed()));
    }
}