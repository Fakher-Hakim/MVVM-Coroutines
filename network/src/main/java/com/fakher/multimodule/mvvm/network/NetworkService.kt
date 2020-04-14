package com.fakher.multimodule.mvvm.network

import com.fakher.multimodule.mvvm.network.dto.TaskDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("todos/")
    suspend fun fetchTasksByUserId(@Query("userId") userId: Int): List<TaskDTO>
}