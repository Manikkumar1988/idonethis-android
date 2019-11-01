package com.mani.idonethis.ui.login.repository

class UserRepository(private val userApiService: UserApiService) {
    suspend fun login(userName: String, password: String) =
        userApiService.login(
            hashMapOf(
                "userId" to userName,
                "password" to password
            )
        )
}
