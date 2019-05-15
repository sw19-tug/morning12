package com.twelve.morning.notebook;


import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class EditNoteTest {

    @Rule
    public ActivityTestRule<MainActivity> activityMainTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private String title_input = "abc";
    private String body_input = "testBodytestBodytestBodytestBodytestBodytestBodytestBodytestBody";

    @Test
    public void setupNoteInOverView(){
        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.rl_create_note)).check(matches(isDisplayed()));
        onView(withId(R.id.et_note_title)).perform(clearText(), typeText(title_input));
        onView(withId(R.id.et_note_body)).perform(clearText(), typeText(body_input), closeSoftKeyboard());
        onView(withId(R.id.et_note_title)).check(matches(withText(title_input)));
        onView(withId(R.id.et_note_body)).check(matches(withText(body_input)));
        onView(withId(R.id.bt_note_create_save)).perform(click());
        onView(withId(R.id.bt_create)).check(matches(isDisplayed()));
    }

    @Test
    public void editFormContainsFieldsAndButtons(){
        onView(withText(title_input)).perform(click());
        onView(withId(R.id.rl_edit_note)).check(matches(isDisplayed()));
        onView(withId(R.id.et_edit_note_title)).check(matches(isDisplayed()));
        onView(withId(R.id.et_edit_note_body)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_edit_note_create_save)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_edit_note_create_cancel)).check(matches(isDisplayed()));
    }

    @Test
    public void returnToMainActivityByAbort(){
        onView(withText(title_input)).perform(click());
        onView(withId(R.id.rl_edit_note)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_edit_note_create_cancel)).perform(click());
        onView(withId(R.id.bt_create)).check(matches(isDisplayed()));
    }

    @Test
    public void returnToMainActivityBySave(){
        onView(withText(title_input)).perform(click());
        onView(withId(R.id.rl_edit_note)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_edit_note_create_save)).perform(click());
        onView(withId(R.id.bt_create)).check(matches(isDisplayed()));
    }

    @Test
    public void listItemTextIsPassedToEditNote(){
        onView(withText(title_input)).perform(click());
        onView(withId(R.id.rl_edit_note)).check(matches(isDisplayed()));
        onView(withId(R.id.et_edit_note_title)).check(matches(withText(title_input)));
        onView(withId(R.id.et_edit_note_body)).check(matches(withText(body_input)));
        onView(withId(R.id.bt_edit_note_create_save)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_edit_note_create_cancel)).check(matches(isDisplayed()));
    }

    @Test
    public void testIfNoteIsActuallyEdited() {
        String title_input_2 = "dummyTitle2";
        String body_input_2 = "dummyBodydummyBodydummyBodydummyBodydummyBodydummyBody2";
        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(clearText(), typeText(title_input_2));
        onView(withId(R.id.et_note_body)).perform(clearText(), typeText(body_input_2))
                .perform(closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());
        onView(withText(title_input_2)).perform(click());
        String new_title = "newTitle";
        String new_body = "newBody";
        onView(withId(R.id.et_edit_note_title)).perform(clearText(), typeText(new_title), closeSoftKeyboard());
        onView(withId(R.id.et_edit_note_body)).perform(clearText(), typeText(new_body), closeSoftKeyboard());

        onView(withId(R.id.bt_edit_note_create_save)).perform(click());
        onView(withText(new_title)).check(matches(isDisplayed()));
    }


    @Test
    public void testShareNote() {

        // create note
        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(typeText("XXXX"), closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());

        // open created note
        onView(withText("XXXX")).perform(click());
        Espresso.openContextualActionModeOverflowMenu();

        onView(withText(R.string.share_note)).check(matches(isDisplayed()));
        onView(withText(R.string.share_note)).perform(click());
        //onView(withText("share " + "XXXX.zip")).perform(click());

    }


}