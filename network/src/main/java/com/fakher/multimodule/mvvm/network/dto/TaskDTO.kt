package com.fakher.multimodule.mvvm.network.dto

data class TaskDTO(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
)