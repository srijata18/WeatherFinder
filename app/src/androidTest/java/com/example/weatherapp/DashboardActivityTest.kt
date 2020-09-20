package com.example.weatherapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.weatherapp.dashboard.views.DashBoardActivity
import junit.framework.Assert.assertTrue
import kotlinx.android.synthetic.main.content_main.*

class DashboardActivityTest {

    private lateinit var stringToBetyped: String
    private lateinit var errorTxt : String

    @get:Rule
    var activityRule: ActivityScenarioRule<DashBoardActivity>
            = ActivityScenarioRule(DashBoardActivity::class.java)

    @Before
    fun initValidString() {
        // Specify a valid string.
        stringToBetyped = "Espresso"
        errorTxt = "Please enter location"
    }

    @Test
    fun checkBtnClick_emptyLocation() {
        Thread.sleep(1000)
        onView(withId(R.id.iv_search_loc)).perform(click())
        //check error visible
        onView(withId(R.id.et_search)).
            check(matches(hasErrorText(errorTxt)))
    }

    @Test
    fun checkBtnClick_filledLocation() {
        Thread.sleep(1000)
        // Type text and then press the button.
        onView(withId(R.id.et_search))
            .perform(typeText(stringToBetyped))
        onView(withId(R.id.iv_search_loc)).perform(click())
        Thread.sleep(3000)
        // perform action opening Alert with text and alert icon, after response
        onView(withId(R.id.alert_msg)).check(matches(isDisplayed()))
        onView(withId(R.id.alert_img)).check(matches(isDisplayed()))
    }

    @Test
    fun checkBtnClick_msgDisplayed(){
        Thread.sleep(1000)
        // Type text and then press the button.
        onView(withId(R.id.et_search))
            .perform(typeText(stringToBetyped))
        // Check that the text was changed.
        onView(withId(R.id.et_search))
            .check(matches(withText(stringToBetyped)))
    }
}