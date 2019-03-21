package com.twelve.morning.notebook;


import android.support.test.espresso.matcher.RootMatchers;
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
public class CreateNoteTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void popUpShowsOnFabClick(){
        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.rl_create_note)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
    }

    @Test
    public void popUpContainsFieldsAndButtons(){
        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.rl_create_note)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.et_note_title)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.et_note_body)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.bt_note_create_save)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.bt_note_create_cancel)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));

    }



}