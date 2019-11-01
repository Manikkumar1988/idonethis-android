package com.mani.idonethis.ui

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withErrorInInputLayout(stringMatcher: Matcher<String>): Matcher<View> {
    checkNotNull(stringMatcher)

    return object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
        internal var actualError = ""

        override fun describeTo(description: Description) {
            description.appendText("with error: ")
            stringMatcher.describeTo(description)
            description.appendText("\nBut got: $actualError")
        }

        public override fun matchesSafely(textInputLayout: TextInputLayout): Boolean {
            val error = textInputLayout.error
            if (error != null) {
                actualError = error.toString()
                return stringMatcher.matches(actualError)
            }
            return false
        }
    }
}
