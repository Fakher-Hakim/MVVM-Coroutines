package com.fakher.multimodule.mvvm.usecase.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fakher.multimodule.mvvm.database.Task
import com.fakher.multimodule.mvvm.database.User
import com.fakher.multimodule.mvvm.repository.TaskRepository
import com.fakher.multimodule.mvvm.repository.UserRepository
import com.fakher.multimodule.mvvm.utils.MainCoroutineScopeRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.Exception

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var taskRepository: TaskRepository

    private val user = User(5, "aze", "aze".hashCode(), "info")

    private val task1 = Task(1, 5, "title1", true)
    private val task2 = Task(2, 5, "title2", false)

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun should_delete_user() {
        val viewModel = MainViewModel(userRepository, taskRepository)

        coEvery { userRepository.deleteUserById(any()) } returns Unit

        viewModel.deleteUser(user)

        assertNull(viewModel.error.value, null)
        assertEquals(viewModel.signout.value, true)
    }

    @Test
    fun should_signout_user() {
        val viewModel = MainViewModel(userRepository, taskRepository)

        viewModel.signOutUser()

        assertNull(viewModel.error.value, null)
        assertEquals(viewModel.signout.value, true)
    }

    @Test
    fun should_load_task_successfully() {
        val viewModel = MainViewModel(userRepository, taskRepository)

        coEvery { taskRepository.getTasksForUser(any()) } returns listOf(task1, task2)

        viewModel.loadTasks(user)

        assertNull(viewModel.error.value, null)
        assertEquals(viewModel.userTasks.value, listOf(task1, task2))
    }
}