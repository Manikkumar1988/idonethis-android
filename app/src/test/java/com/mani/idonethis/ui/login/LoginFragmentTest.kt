package com.mani.idonethis.ui.login

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import com.mani.idonethis.R
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = KoinTestApp::class, sdk = [28])
class LoginFragmentTest : KoinTest() {

    @MockK
    lateinit var loginViewModel: LoginViewModel

    @Before
    fun `set up`() {
        MockKAnnotations.init(this)
        loadKoinModules(module {
            single(override = true) { loginViewModel }
        })

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

}