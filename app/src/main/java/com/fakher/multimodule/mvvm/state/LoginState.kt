package com.fakher.multimodule.mvvm.state

import com.fakher.multimodule.mvvm.database.User

object LoginState {
    private var isLoggedIn = false
    private var user: User? = null

    fun login(user: User) {
        isLoggedIn = true
        LoginState.user = user
    }

    fun logout() {
        isLoggedIn = false
        user = null
    }

}