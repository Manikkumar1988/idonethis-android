package com.mani.idonethis.ui.login.repository

import com.mani.idonethis.ui.login.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class UserRepositoryTest {
    @MockK
    lateinit var userApiService: UserApiService

    @Before
    fun `set up`() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should make login request and pass back the response`() {
        val userName = "abc@test.com"
        val password = "pass"
        val user = User(userName, password, "1")

        val userRepository = UserRepository(userApiService)

        coEvery {
            userApiService.login(
                hashMapOf(
                    "userId" to userName,
                    "password" to password
                )
            )
        } returns Response.success(user)

        runBlocking {
            val result = userRepository.login(userName, password)
            TestCase.assertEquals(user, result.body())
        }
    }
}