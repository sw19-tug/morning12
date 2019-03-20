package com.twelve.morning.notebook;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class NoteOverviewTest {

    @Test
    public void testAddButtonIsDisplayed() {
        onView(withId(R.id.bt_create)).check(matches(isDisplayed()));
    }
}
