package com.example.twistermandatoryassignment;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.twistermandatoryassignment.ui.RegisterActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.twistermandatoryassignment", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<RegisterActivity> activityScenarioRule
            = new ActivityScenarioRule<>(RegisterActivity.class);

    @Test
    public void testElementsUsable(){
        onView(withId(R.id.editTextRegisterName)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.editTextRegisterEmail)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.editTextRegisterPassword1)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.editTextRegisterPassword2)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.buttonRegisterBack)).check(matches(isClickable()));
        onView(withId(R.id.buttonRegisterCreate)).check(matches(isClickable()));
    }

    @Test
    public void testRegistrationNameError() {
        //Name error (must be A-Z, no spaces)
        onView(withId(R.id.editTextRegisterName)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.buttonRegisterCreate)).perform(click());
        onView(withId(R.id.editTextRegisterName)).check(matches(hasErrorText("Please enter a valid name")));
    }

    @Test
    public void testRegistrationEmailError() {
        onView(withId(R.id.editTextRegisterName)).perform(typeText("Test"), closeSoftKeyboard());
        //Email error (must be a valid email format)
        onView(withId(R.id.editTextRegisterEmail)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.buttonRegisterCreate)).perform(click());
        onView(withId(R.id.editTextRegisterEmail)).check(matches(hasErrorText("Please enter a valid email address")));
    }

    @Test
    public void testRegistrationPasswordLengthError() {
        onView(withId(R.id.editTextRegisterName)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.editTextRegisterEmail)).perform(typeText("Test@gmail.com"), closeSoftKeyboard());
        //Password error (must be at least 6 characters)
        onView(withId(R.id.editTextRegisterPassword1)).perform(typeText("12345"), closeSoftKeyboard());
        onView(withId(R.id.buttonRegisterCreate)).perform(click());
        onView(withId(R.id.editTextRegisterPassword1)).check(matches(hasErrorText("Entered passwords do not match or are too short")));
    }

    @Test
    public void testRegistrationPasswordMatchError(){
        onView(withId(R.id.editTextRegisterName)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.editTextRegisterEmail)).perform(typeText("Test@gmail.com"), closeSoftKeyboard());
        //Password error (must match)
        onView(withId(R.id.editTextRegisterPassword1)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.editTextRegisterPassword2)).perform(typeText("123457"), closeSoftKeyboard());
        onView(withId(R.id.buttonRegisterCreate)).perform(click());
        onView(withId(R.id.editTextRegisterPassword2)).check(matches(hasErrorText("Entered passwords do not match or are too short")));
    }
}