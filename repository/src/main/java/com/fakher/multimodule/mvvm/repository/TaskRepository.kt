package com.fakher.multimodule.mvvm.repository

import com.fakher.multimodule.mvvm.database.Task
import com.fakher.multimodule.mvvm.database.TaskDao
import com.fakher.multimodule.mvvm.network.NetworkApi
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao, private val networkApi: NetworkApi) {

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    suspend fun getTaskById(id: Int) = taskDao.getTaskById(id)

    suspend fun getTasksForUser(userId: Int): List<Task> {
        val tasks = networkApi.fetchTasksByUserId(userId)
            .map { taskDTO ->
                Task(
                    taskDTO.id,
                    taskDTO.userId,
                    taskDTO.title,
                    taskDTO.completed
                )
            }
        taskDao.insertAll(tasks)
        return tasks
    }
}