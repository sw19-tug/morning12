package com.twelve.morning.notebook;

import android.Manifest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class SearchTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION);

    @Before
    public void resetDatabase(){
        DatabaseWrapper.getInstance().reset();
    }

    @Test
    public void searchBarTest() {
        String title_input = "dummyTitle";
        String body_input = "dummyBodydummyBodydummyBodydummyBodydummyBodydummyBody";
        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(clearText(), typeText(title_input), closeSoftKeyboard());
        onView(withId(R.id.et_note_body)).perform(clearText(), typeText(body_input), closeSoftKeyboard())
                .perform(closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());

        onView(withText(title_input)).perform(click());

        onView(withId(R.id.search_view_find_text)).check(matches(isDisplayed()));
    }

    @Test
    public void textSearcherTest() {
        Note note = new Note();
        note.setBody("abcabcabcabcabcabc");

        int pos = TextSearcher.GetInstance().SearchNextInstance(note, "abc");
        assertEquals(0, pos);

        pos = TextSearcher.GetInstance().SearchNextInstance(note, "abc");
        assertEquals(3, pos);

        pos = TextSearcher.GetInstance().SearchNextInstance(note, "dummy");
        assertEquals(-1, pos);
    }


}
