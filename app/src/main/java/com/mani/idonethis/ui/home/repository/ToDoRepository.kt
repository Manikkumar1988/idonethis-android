package com.mani.idonethis.ui.home.repository

import com.mani.idonethis.ui.home.model.DoneItem

class ToDoRepository(private val toDoService: ToDoService) {
    suspend fun addTodo(userName: String, doneItem: DoneItem) =
        toDoService.addToDo(userName, doneItem)
}
