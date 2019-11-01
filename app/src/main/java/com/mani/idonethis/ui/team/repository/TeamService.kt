package com.mani.idonethis.ui.team.repository

import com.mani.idonethis.ui.home.model.DoneItem
import com.mani.idonethis.ui.team.model.Team
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamService {
    @POST("/team/1")
    suspend fun addToTeam(@Body team: Team): Response<Team>

    @GET("/team/1")
    suspend fun getTeam(): Response<List<Team>>
}
