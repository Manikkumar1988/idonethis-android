package com.mani.idonethis.ui.home

import com.mani.idonethis.UserSharedPreference
import com.mani.idonethis.ui.MainCoroutineRule
import com.mani.idonethis.ui.home.model.DoneItem
import com.mani.idonethis.ui.home.repository.ToDoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class HomeViewModelTest {

    @get:Rule
    var mainCoRoutineRule = MainCoroutineRule()

    @MockK
    lateinit var toDoRepository: ToDoRepository

    @MockK
    lateinit var userSharedPreference: UserSharedPreference

    lateinit var homeViewModel: HomeViewModel

    @Before
    fun `set up`() {
        MockKAnnotations.init(this)
        homeViewModel = HomeViewModel(toDoRepository, userSharedPreference)
    }

    @Test
    fun `should return added done item` () {
        val userName = "1"
        val doneItem = DoneItem("Task A", 1)

        every { userSharedPreference.getUserId() } returns userName
        coEvery { toDoRepository.addTodo(userName, doneItem) } returns Response.success(200, doneItem)

        homeViewModel.addTodo(doneItem)

        coVerify { toDoRepository.addTodo(userName, doneItem) }
    }
}