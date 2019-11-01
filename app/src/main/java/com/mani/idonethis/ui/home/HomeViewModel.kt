package com.mani.idonethis.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mani.idonethis.UserSharedPreference
import com.mani.idonethis.ui.home.model.DoneItem
import com.mani.idonethis.ui.home.repository.ToDoRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val toDoRepository: ToDoRepository,
                    private val userSharedPreference: UserSharedPreference) : ViewModel() {

    fun addTodo(doneItem: DoneItem) {
        val userId = userSharedPreference.getUserId()
        viewModelScope.launch {
            toDoRepository.addTodo(userId, doneItem)
        }
    }

}