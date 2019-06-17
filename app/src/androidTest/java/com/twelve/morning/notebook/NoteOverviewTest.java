package com.twelve.morning.notebook;

import android.Manifest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class NoteOverviewTest {

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
    public void testAddButtonIsDisplayed() {
        onView(withId(R.id.bt_create)).check(matches(isDisplayed()));
    }

    @Test
    public void testNotesListViewIsDisplayed() {
        onView(withId(R.id.list_notes)).check(matches(isDisplayed()));
    }

    @Test
    public void testNotesListAddNote() {
        onView(withId(R.id.bt_create)).perform(click());
        onView(withId(R.id.et_note_title)).perform(typeText("Pizza"), closeSoftKeyboard());
        onView(withId(R.id.et_note_body)).perform(typeText("- Sale Marina\n- Farina W220\n- Lievito di birra\n- Mozzarella di Bufala Campana DOP\n- Basilico fresco\n- Pomodori pelati\n- Olio extra vergine"), closeSoftKeyboard());
        onView(withId(R.id.bt_note_create_save)).perform(click());
        onView(withText("Pizza")).check(matches(isDisplayed()));
    }
}
