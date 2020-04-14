package com.fakher.multimodule.mvvm.usecase.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakher.multimodule.mvvm.database.User
import com.fakher.multimodule.mvvm.repository.UserRepository
import com.fakher.multimodule.mvvm.state.LoginState
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignupViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {

    val newUser = MutableLiveData<User>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun signup(username: String, password: String, info: String) {
        viewModelScope.launch {
            loading.value = true
            val validInput = checkInputDataValid(username, password, info)
            if (!validInput) {
                loading.value = false
                error.value = "Please fill all fields!"
            } else {
                createUser(username, password, info)
            }
        }
    }

    private suspend fun createUser(username: String, password: String, info: String) {
        val user = userRepository.getUserByName(username)
        if (user != null) {
            loading.value = false
            error.value = "User already exists!"
        } else {
            val newUser = User(
                username = username,
                passwordHash = password.hashCode(),
                info = info
            )
            val userId = userRepository.insertUser(newUser).toInt()
            newUser.id = userId
            LoginState.login(newUser)
            loading.value = false
            this.newUser.value = newUser
        }
    }

    private fun checkInputDataValid(username: String, password: String, info: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty() && info.isNotEmpty()
    }

}