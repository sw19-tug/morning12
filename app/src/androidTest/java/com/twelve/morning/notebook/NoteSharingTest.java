package com.twelve.morning.notebook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class NoteSharingTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void resetDatabase(){
        DatabaseWrapper.getInstance().reset();

    }

    @Test
    public void menuButtonOverviewTest() {
        Intent resultData = new Intent();
        String path = "uh.zip";
        resultData.putExtra("path", path);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        Intents.intending(IntentMatchers.anyIntent()).respondWith(result);

        Espresso.openContextualActionModeOverflowMenu();
        onView(withText(R.string.import_string)).check(matches(isDisplayed()));
        onView(withText(R.string.import_string)).perform(click());
        onView(withText(R.string.import_string)).check(doesNotExist());
    }
}