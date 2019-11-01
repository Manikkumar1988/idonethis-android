package com.mani.idonethis.ui.team

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mani.idonethis.ui.home.repository.TeamRepository
import com.mani.idonethis.ui.team.model.Team
import kotlinx.coroutines.launch

class TeamViewModel(val teamRepository: TeamRepository) : ViewModel() {

    val teamMutableLiveData = MutableLiveData<List<Team>>()

    fun addToTeam(mailID: String) {
        val team = Team(mailID,"1")
        viewModelScope.launch {
            teamRepository.addToTeam(team)
        }
    }

    fun getTeam() {
        viewModelScope.launch {
            val teamListResponse = teamRepository.getTeam()
            if(teamListResponse.isSuccessful) {
                teamMutableLiveData.value = teamListResponse.body()
                Log.d("Boolah", teamMutableLiveData.value?.toString())
            }
        }
    }

}