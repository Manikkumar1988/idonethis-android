package com.mani.idonethis.ui.home.repository

import com.mani.idonethis.ui.home.model.DoneItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ToDoService {
    @POST("/user/{uid}/item")
    suspend fun addToDo(@Path("uid") uid: String, @Body doneItem: DoneItem): Response<DoneItem>
}
