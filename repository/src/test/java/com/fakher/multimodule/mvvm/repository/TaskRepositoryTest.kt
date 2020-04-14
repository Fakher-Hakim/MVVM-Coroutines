package com.fakher.multimodule.mvvm.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fakher.multimodule.mvvm.database.Task
import com.fakher.multimodule.mvvm.database.TaskDao
import com.fakher.multimodule.mvvm.network.NetworkApi
import com.fakher.multimodule.mvvm.network.dto.TaskDTO
import com.fakher.multimodule.mvvm.utils.MainCoroutineScopeRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TaskRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    private lateinit var taskDao: TaskDao

    @MockK
    private lateinit var networkApi: NetworkApi

    private lateinit var taskRepository: TaskRepository

    private val task1 = Task(1, 5, "title1", true)
    private val task2 = Task(2, 5, "title2", false)

    private val taskDTO1 = TaskDTO(1, 5, "title1", true)
    private val taskDTO2 = TaskDTO(2, 5, "title2", false)

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        taskRepository = TaskRepository(taskDao, networkApi)
    }

    @Test
    fun should_fetch_tasks_success() {
        val tasksDTO = listOf(taskDTO1, taskDTO2)
        val tasks = listOf(task1, task2)
        runBlockingTest {
            coEvery { networkApi.fetchTasksByUserId(any()) } returns tasksDTO
            val result = taskRepository.getTasksForUser(5)

            coVerify { taskDao.insertAll(tasks) }
            Assert.assertEquals(result, tasks)
        }
    }

    @Test
    fun should_insert_task_success() {
        runBlockingTest {
            coEvery { taskDao.insertTask(any()) } returns 1

            val result = taskRepository.insertTask(task1)

            Assert.assertEquals(result, 1)
            coVerify { taskDao.insertTask(task1) }
        }
    }

    @Test
    fun should_get_task_success() {
        runBlockingTest {
            coEvery { taskDao.getTaskById(any()) } returns task1

            val result = taskRepository.getTaskById(1)

            Assert.assertEquals(result, task1)
            coVerify { taskDao.getTaskById(1) }
        }
    }
}