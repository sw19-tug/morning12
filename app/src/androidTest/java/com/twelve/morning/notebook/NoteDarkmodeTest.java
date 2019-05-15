package com.twelve.morning.notebook;

import android.content.res.TypedArray;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class NoteDarkmodeTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testDarkmodeButtonIsDisplayed() {
        Espresso.openContextualActionModeOverflowMenu();
        onView(withText(R.string.night_mode)).check(matches(isDisplayed()));
        Espresso.pressBack();
    }

    @Test
    public void testColorChanged() {
        Espresso.openContextualActionModeOverflowMenu();
        onView(withText(R.string.night_mode)).check(matches(isDisplayed()));
        onView(withText(R.string.night_mode)).perform(click());
        Espresso.openContextualActionModeOverflowMenu();
        onView(withText(R.string.day_mode)).check(matches(isDisplayed()));
        Espresso.pressBack();

        TypedArray day_values = activityTestRule.getActivity().getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.windowBackground
        });

        Espresso.openContextualActionModeOverflowMenu();
        onView(withText(R.string.day_mode)).check(matches(isDisplayed()));
        onView(withText(R.string.day_mode)).perform(click());

        TypedArray night_values = activityTestRule.getActivity().getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.windowBackground
        });

        for(int i = 0; i < day_values.length(); i++){
            int old_color = day_values.getColor(i, 0xFF00FF);
            int new_color = night_values.getColor(i, 0xFF00FF);
            Assert.assertNotEquals(old_color, new_color);
        }


    }
}
