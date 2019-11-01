package com.mani.idonethis.ui.login

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mani.idonethis.MainActivity
import com.mani.idonethis.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginFragmentTestEspresso {

    @get:Rule
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    @Before
    fun setup() {

    }


    @Test
    @Throws(Exception::class)
    fun testQuoteIsShown() {
        val intent = Intent()
        activityTestRule.launchActivity(intent)

        onView(withId(R.id.textInputEditTextUserName)).perform(ViewActions.click())
        onView(withId(R.id.textInputEditTextUserName)).perform(ViewActions.typeText("test1@gmail.com"))

        onView(withId(R.id.textInputEditTextPassword)).perform(ViewActions.click())
        onView(withId(R.id.textInputEditTextPassword)).perform(ViewActions.typeText("pass"))

        onView(withId(R.id.loginButton)).perform(ViewActions.click())

        Thread.sleep(10000)

        onView(withId(R.id.text_home)).check(matches(isDisplayed()))
    }

}