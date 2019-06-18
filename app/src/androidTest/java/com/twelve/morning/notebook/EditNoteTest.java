package com.twelve.morning.notebook;


import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
public class EditNoteTest {

    public static Matcher<Intent> chooser(Matcher<Intent> matcher) {
        return allOf(hasAction(Intent.ACTION_CHOOSER), hasExtra(is(Intent.EXTRA_INTENT), matcher));
    }


    @Rule
    public ActivityTestRule<MainActivity> activityMainTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION);

    private String title_input = "abc";
    private String body_input = "testBody";


    @Before
    public void resetDatabaseAndSetupNoteToEdit(){
        DatabaseWrapper.getInstance().reset();
        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.rl_create_note)).check(matches(isDisplayed()));
        onView(withId(R.id.et_note_title)).perform(clearText(), typeText(title_input), closeSoftKeyboard());
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
        onView(withId(R.id.et_note_title)).perform(clearText(), typeText(title_input_2), closeSoftKeyboard());
        onView(withId(R.id.et_note_body)).perform(clearText(), typeText(body_input_2), closeSoftKeyboard());
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
        onView(withText(title_input)).perform(click());
        Espresso.openContextualActionModeOverflowMenu();
        onView(withText(R.string.share_note)).check(matches(isDisplayed()));
    }


}