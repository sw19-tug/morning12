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
public class SocialMediaTest {

    @Rule
    public ActivityTestRule<CreateNoteActivity> activityCreateNoteTestRule =
            new ActivityTestRule<>(CreateNoteActivity.class);
    
    @Test
    public void checkSocialButton() {
        onView(withId(R.id.bt_socialButton)).check(matches(isDisplayed()));
    }

    @Test
    public void checkCompcheckSocialLinks() {
        onView(withId(R.id.bt_socialLinks)).check(matches(isDisplayed()));
    } 

   
}