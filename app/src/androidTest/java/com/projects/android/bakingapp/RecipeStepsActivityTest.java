package com.projects.android.bakingapp;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

public class RecipeStepsActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickingOnIngredientsOpensStepsDetailActivityWithIngredients(){
        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_steps))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tv_ingredients_list)).check(matches(not(withText(""))));
    }

    @Test
    public void clickingOnStepsOpensStepsDetailActivityWithIngredientsGoneAndDetailsVisible(){
        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_steps))
                .perform(actionOnItemAtPosition(1, click()));
        onView(withId(R.id.tv_ingredients_list)).check(matches(not(isDisplayed())));
        onView(withId(R.id.tv_step_detail)).check(matches(isDisplayed()));
    }
}
