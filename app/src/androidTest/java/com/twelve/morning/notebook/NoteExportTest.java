package com.twelve.morning.notebook;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@Ignore
@RunWith(AndroidJUnit4.class)
public class NoteExportTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void resetDatabase(){
        DatabaseWrapper.getInstance().reset();
    }

    @Test
    public void checkVisibleButton () {
        Espresso.openContextualActionModeOverflowMenu();
        onView(withId(R.id.bt_export)).check(matches(isDisplayed()));
    }

    @Test
    public void checkClickableButton () {
        onView(withId(R.id.bt_export)).perform(click());
    }

    @Test
    public void checkCompress() {
        Espresso.openContextualActionModeOverflowMenu();
        onView(withId(R.id.bt_export)).check(matches(isDisplayed()));
    }
}