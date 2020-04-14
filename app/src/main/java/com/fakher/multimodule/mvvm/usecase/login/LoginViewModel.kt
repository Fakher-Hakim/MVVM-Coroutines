package com.fakher.multimodule.mvvm.usecase.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakher.multimodule.mvvm.database.User
import com.fakher.multimodule.mvvm.repository.UserRepository
import com.fakher.multimodule.mvvm.state.LoginState
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val loggedUser = MutableLiveData<User>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun login(username: String, password: String) {
        loading.value = true
        viewModelScope.launch {
            val isValidInput = checkInputValid(username, password)
            if (!isValidInput) {
                loading.value = false
                error.value = "Please fill all fields!"
            } else {
                makeLogin(username, password)
            }
        }
    }

    private suspend fun makeLogin(username: String, password: String) {
        val user = userRepository.getUserByName(username)
        when {
            user == null -> {
                loading.value = false
                error.value = "User not found!"
            }
            user.passwordHash != password.hashCode() -> {
                loading.value = false
                error.value = "Password is wrong!"
            }
            else -> {
                LoginState.login(user)
                loading.value = false
                loggedUser.value = user
            }
        }
    }

    private fun checkInputValid(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }

}