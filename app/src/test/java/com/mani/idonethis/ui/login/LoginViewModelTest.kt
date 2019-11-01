package com.mani.idonethis.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.mani.idonethis.UserSharedPreference
import com.mani.idonethis.ui.MainCoroutineRule
import com.mani.idonethis.ui.login.model.User
import com.mani.idonethis.ui.login.repository.UserRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class LoginViewModelTest {

    @get:Rule
    var mainCoRoutineRule = MainCoroutineRule()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    private lateinit var userSharedPreference: UserSharedPreference

    private lateinit var loginViewModel: LoginViewModel

    private val validUserName = "abc@gmail.com"
    private val validPassword = "pass"

    @Before
    fun `set up`() {
        MockKAnnotations.init(this)

        loginViewModel =
            LoginViewModel(userRepository, userSharedPreference)
    }

    @Test
    fun `should validate username as invalid`() {
        assertNull(LiveDataTestUtil.getValue(loginViewModel.isValidUserName))
        loginViewModel.userName.value = ""
        loginViewModel.password.value = validPassword
        LiveDataTestUtil.getValue(loginViewModel.loginButtonStatus)

        loginViewModel.login()

        assertFalse(LiveDataTestUtil.getValue(loginViewModel.isValidUserName))
        coVerify(exactly = 0) { userRepository.login(validPassword, "123") }
    }

    @Test
    fun `should validate password number as invalid`() {
        assertNull(LiveDataTestUtil.getValue(loginViewModel.isValidPassword))
        loginViewModel.userName.value = validUserName
        loginViewModel.password.value = ""
        LiveDataTestUtil.getValue(loginViewModel.loginButtonStatus)

        loginViewModel.login()

        assertFalse(LiveDataTestUtil.getValue(loginViewModel.isValidPassword))
        coVerify(exactly = 0) { userRepository.login("123", validUserName) }
    }

    @Test
    fun `should validate userName as valid`() {
        assertNull(LiveDataTestUtil.getValue(loginViewModel.isValidUserName))
        loginViewModel.userName.value = "1234567890123"
        loginViewModel.password.value = "123"
        LiveDataTestUtil.getValue(loginViewModel.loginButtonStatus)
        coEvery {
            userRepository.login("1234567890123", "123")
        } returns Response.success(User(validUserName, validPassword, "1"))

        every { userSharedPreference.addUser("1") } just runs

        loginViewModel.login()

        assertTrue(LiveDataTestUtil.getValue(loginViewModel.isValidUserName))
    }

    @Test
    fun `should validate password as valid`() {
        assertNull(LiveDataTestUtil.getValue(loginViewModel.isValidPassword))
        loginViewModel.userName.value = validUserName
        loginViewModel.password.value = validPassword
        LiveDataTestUtil.getValue(loginViewModel.loginButtonStatus)

        coEvery {
            userRepository.login(validUserName, validPassword)
        } returns Response.success(User(validUserName, validPassword, "1"))
        every { userSharedPreference.addUser("1") } just runs

        loginViewModel.login()

        assertTrue(LiveDataTestUtil.getValue(loginViewModel.isValidPassword))
    }

    @Test
    fun `should not make logic call when username is empty`() {
        loginViewModel.userName.value = ""
        loginViewModel.password.value = validPassword

        loginViewModel.login()

        assertFalse(LiveDataTestUtil.getValue(loginViewModel.isValidUserName))
        coVerify(exactly = 0) { userRepository.login(validPassword, "") }
    }

    @Test
    fun `should not make login call when password is empty`() {
        loginViewModel.userName.value = validUserName
        loginViewModel.password.value = ""

        loginViewModel.login()

        assertFalse(LiveDataTestUtil.getValue(loginViewModel.isValidPassword))
        coVerify(exactly = 0) { userRepository.login("", validUserName) }
    }

    @Test
    fun `should not make verify call when both userName and password is empty`() {
        loginViewModel.userName.value = ""
        loginViewModel.password.value = ""

        loginViewModel.login()

        assertFalse(LiveDataTestUtil.getValue(loginViewModel.isValidPassword))
        coVerify(exactly = 0) { userRepository.login("", "") }
    }

    @Test
    fun `should set error when login fails`() {
        loginViewModel.userName.value = validUserName
        loginViewModel.password.value = validPassword
        
        coEvery {
            userRepository.login(validUserName, validPassword)
        } returns Response.error(
            500, ResponseBody.create(null, "Failed to register user")
        )
        LiveDataTestUtil.getValue(loginViewModel.loginButtonStatus)

        loginViewModel.login()

        assertNull(LiveDataTestUtil.getValue(loginViewModel.userMutableLiveData))
        coVerify { userRepository.login(validUserName, validPassword) }
        assertEquals(
            LoginButtonStatus.ENABLED,
            LiveDataTestUtil.getValue(loginViewModel.loginButtonStatus)
        )
    }


    @Test
    fun `should login for valid username and password`() {
        loginViewModel.userName.value = validUserName
        loginViewModel.password.value = validPassword

        coEvery {
            userRepository.login(validUserName, validPassword)
        } returns Response.success(User(validUserName, validPassword, "1"))

        every { userSharedPreference.addUser("1") } just runs
        LiveDataTestUtil.getValue(loginViewModel.loginButtonStatus)


        loginViewModel.login()

        assertEquals(
            LoginButtonStatus.ENABLED,
            LiveDataTestUtil.getValue(loginViewModel.loginButtonStatus)
        )
        assertEquals(
            User(validUserName, validPassword, "1"),
            LiveDataTestUtil.getValue(loginViewModel.userMutableLiveData)
        )
        coVerify { userRepository.login(validUserName, validPassword) }
    }


}