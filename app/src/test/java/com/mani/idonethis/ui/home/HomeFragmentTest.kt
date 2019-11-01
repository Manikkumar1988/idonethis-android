package com.mani.idonethis.ui.home

import androidx.fragment.app.testing.launchFragmentInContainer
import com.mani.idonethis.R
import com.mani.idonethis.ui.KoinTest
import com.mani.idonethis.ui.login.KoinTestApp
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = KoinTestApp::class, sdk = [28])
class HomeFragmentTest : KoinTest() {

    @Test
    fun `should launch Home Fragment`() {
        launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)
    }

}