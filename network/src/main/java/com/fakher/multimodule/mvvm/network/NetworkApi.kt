package com.fakher.multimodule.mvvm.network

import com.fakher.multimodule.mvvm.network.dto.TaskDTO
import javax.inject.Inject

class NetworkApi @Inject constructor(val networkService: NetworkService) {

    suspend fun fetchTasksByUserId(userId: Int): List<TaskDTO> {
        return networkService.fetchTasksByUserId(userId)
    }
}