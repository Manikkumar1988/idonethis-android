package com.mani.idonethis.ui.home.repository

import com.mani.idonethis.ui.team.model.Team
import com.mani.idonethis.ui.team.repository.TeamService

class TeamRepository(private val teamService: TeamService) {
    suspend fun addToTeam(team: Team) =
        teamService.addToTeam(team)

    suspend fun getTeam() = teamService.getTeam()
}
