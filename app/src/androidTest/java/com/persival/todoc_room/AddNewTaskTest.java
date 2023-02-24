package com.persival.todoc_room;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.persival.todoc_room.ui.main.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddNewTaskTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
        new ActivityScenarioRule<>(MainActivity.class);

    private static Matcher<View> childAtPosition(
        final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                    && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Test
    public void addNewTaskTest() {
        ViewInteraction floatingActionButton = onView(
            allOf(withId(R.id.fab_add_task), withContentDescription("Ajouter une tâche"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    2),
                isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
            allOf(withId(R.id.txt_task_name),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.custom),
                        0),
                    0),
                isDisplayed()));
        appCompatEditText.perform(replaceText("Laver"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
            allOf(withId(android.R.id.button1), withText("Ajouter"),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.buttonPanel),
                        0),
                    3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction floatingActionButton2 = onView(
            allOf(withId(R.id.fab_add_task), withContentDescription("Ajouter une tâche"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    2),
                isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatEditText2 = onView(
            allOf(withId(R.id.txt_task_name),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.custom),
                        0),
                    0),
                isDisplayed()));
        appCompatEditText2.perform(replaceText("Ranger"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
            allOf(withId(R.id.project_spinner), withContentDescription("Choix du projet"),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.custom),
                        0),
                    1),
                isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
            .inAdapterView(allOf(withId(com.google.android.material.R.id.select_dialog_listview),
                childAtPosition(
                    withId(com.google.android.material.R.id.contentPanel),
                    0)))
            .atPosition(1);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatButton2 = onView(
            allOf(withId(android.R.id.button1), withText("Ajouter"),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.buttonPanel),
                        0),
                    3)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction floatingActionButton3 = onView(
            allOf(withId(R.id.fab_add_task), withContentDescription("Ajouter une tâche"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    2),
                isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction appCompatEditText3 = onView(
            allOf(withId(R.id.txt_task_name),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.custom),
                        0),
                    0),
                isDisplayed()));
        appCompatEditText3.perform(replaceText("Nettoyer"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner2 = onView(
            allOf(withId(R.id.project_spinner), withContentDescription("Choix du projet"),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.custom),
                        0),
                    1),
                isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
            .inAdapterView(allOf(withId(com.google.android.material.R.id.select_dialog_listview),
                childAtPosition(
                    withId(com.google.android.material.R.id.contentPanel),
                    0)))
            .atPosition(2);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatButton3 = onView(
            allOf(withId(android.R.id.button1), withText("Ajouter"),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.buttonPanel),
                        0),
                    3)));
        appCompatButton3.perform(scrollTo(), click());
    }
}
