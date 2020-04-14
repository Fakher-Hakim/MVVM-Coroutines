package com.fakher.multimodule.mvvm.usecase.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakher.multimodule.mvvm.database.Task
import com.fakher.multimodule.mvvm.database.User
import com.fakher.multimodule.mvvm.repository.TaskRepository
import com.fakher.multimodule.mvvm.repository.UserRepository
import com.fakher.multimodule.mvvm.state.LoginState
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(
    val userRepository: UserRepository,
    val taskRepository: TaskRepository
) : ViewModel() {

    val userTasks = MutableLiveData<List<Task>>()
    val userDeleted = MutableLiveData<Boolean>()
    val signout = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun signOutUser() {
        LoginState.logout()
        signout.value = true
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userRepository.deleteUserById(user.id)
            LoginState.logout()
            signout.value = true
        }
    }

    fun loadTasks(user: User) {
        viewModelScope.launch {
            loading.value = true
            try {
                userTasks.value = taskRepository.getTasksForUser(user.id)
            } catch (e: Exception) {
                Log.e(MainViewModel::class.simpleName, "error: ${e.localizedMessage}")
                error.value = "Could not fetch tasks!"
            } finally {
                loading.value = false
            }
        }
    }

}