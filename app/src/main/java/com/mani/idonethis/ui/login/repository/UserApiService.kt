package com.mani.idonethis.ui.login.repository

import com.mani.idonethis.ui.login.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface UserApiService {
    @GET("/user")
    suspend fun login(@HeaderMap hashMapOf: HashMap<String, String>): Response<User>
}
