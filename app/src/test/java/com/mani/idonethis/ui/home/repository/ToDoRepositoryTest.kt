package com.mani.idonethis.ui.home.repository

import com.mani.idonethis.ui.home.model.DoneItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ToDoRepositoryTest {

    @MockK
    lateinit var toDoService: ToDoService

    @Before
    fun `set up`() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should make add to do api`() {
        val userRepository = ToDoRepository(toDoService)

        val uId = "1"
        val doneItem = DoneItem("Task A", 0)
        coEvery {
            toDoService.addToDo(
                uId,
                doneItem
            )
        } returns Response.success(doneItem)

        runBlocking {
            val result = userRepository.addTodo(uId, doneItem)
            TestCase.assertEquals(doneItem, result.body())
        }
    }
}