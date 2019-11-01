package com.mani.idonethis.ui.login

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.mani.idonethis.R
import com.mani.idonethis.ui.KoinTest
import com.mani.idonethis.ui.login.model.User
import com.mani.idonethis.ui.withErrorInInputLayout
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowAlertDialog

@RunWith(RobolectricTestRunner::class)
@Config(application = KoinTestApp::class, sdk = [28])
class LoginFragmentTest : KoinTest() {

    @MockK
    lateinit var loginViewModel: LoginViewModel

    @MockK
    private lateinit var navController: NavController

    @Before
    fun `set up`() {
        MockKAnnotations.init(this)
        loadKoinModules(module {
            single(override = true) { loginViewModel }
        })

        every { loginViewModel.userMutableLiveData } returns MutableLiveData()
        every { loginViewModel.userName } returns MutableLiveData()
        every { loginViewModel.password } returns MutableLiveData()
        every { loginViewModel.loginButtonStatus } returns MutableLiveData(
            LoginButtonStatus.ENABLED)

        every { loginViewModel.isValidUserName } returns MutableLiveData()
        every { loginViewModel.isValidPassword } returns MutableLiveData()
    }

    @Test
    fun `should launch login fragment`() {
        launchFragmentInContainer<LoginFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun `should render login and password fields`() {
        launchFragmentInContainer<LoginFragment>(null, R.style.AppTheme)

        Espresso.onView(ViewMatchers.withId(R.id.textInputEditTextUserName))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))
        Espresso.onView(ViewMatchers.withId(R.id.textInputEditTextPassword))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))

            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
            .check(ViewAssertions.matches(ViewMatchers.withText("Login")))
    }

    @Test
    fun `should render with verify button enabled`() {
        launchFragmentInContainer<LoginFragment>(null, R.style.AppTheme)

        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
            .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
    }



    @Test
    fun `should show citizen id error and phone number error when both are invalid`() {
        every { loginViewModel.isValidUserName } returns MutableLiveData<Boolean>(false)
        every { loginViewModel.isValidPassword } returns MutableLiveData<Boolean>(
            false
        )

        launchFragmentInContainer<LoginFragment>(null, R.style.AppTheme)

        Espresso.onView(ViewMatchers.withId(R.id.textInputLayoutUserName))
            .check(ViewAssertions.matches(withErrorInInputLayout(`is`("Something went wrong"))))

        Espresso.onView(ViewMatchers.withId(R.id.textInputLayoutPassword))
            .check(ViewAssertions.matches(withErrorInInputLayout(`is`("Something went wrong"))))

    }

    @Test
    fun `should show alert with error on user login failure`() {
        val userLiveData = MutableLiveData<User>()
        every { loginViewModel.userMutableLiveData } returns userLiveData
        launchFragmentInContainer<LoginFragment>(null, R.style.AppTheme)

        userLiveData.value = User("", "", "-1")

        val shownAlertDialogs = ShadowAlertDialog.getShownDialogs()
        Assert.assertEquals(1, shownAlertDialogs.size)
        val alertDialog = shownAlertDialogs.first() as AlertDialog
        val alertDialogShadow = Shadows.shadowOf(alertDialog)
        Assert.assertTrue(alertDialog.isShowing)
        Assert.assertTrue(alertDialogShadow.isCancelable)
        Assert.assertTrue(alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick())
        Assert.assertFalse(alertDialog.isShowing)
    }


    @Test
    fun `should navigate to Home fragment on successful user login`() {
        val userLiveData = MutableLiveData<User>()
        val userName = MutableLiveData<String>("1111111111111")
        val password = MutableLiveData<String>("123456790")

        every { loginViewModel.userMutableLiveData } returns userLiveData
        every { loginViewModel.userName } returns userName
        every { loginViewModel.password } returns password

        every { navController.navigate(R.id.action_nav_send_to_nav_home) } returns Unit

        launchFragmentInContainer<LoginFragment>(
            null,
            R.style.AppTheme
        ).onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }
        userLiveData.value = User("", "", "1")

        verify {
            navController.navigate(R.id.action_nav_send_to_nav_home)
        }
    }

}