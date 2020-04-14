package com.fakher.multimodule.mvvm.repository

import android.content.Context
import com.fakher.multimodule.mvvm.database.MainDatabase
import com.fakher.multimodule.mvvm.database.User
import com.fakher.multimodule.mvvm.database.UserDao

class UserRepository(val userDao: UserDao) {

    suspend fun insertUser(newUser: User) = userDao.insertUser(newUser)

    suspend fun getUserByName(username: String) = userDao.getUser(username)

    suspend fun getAll(): List<User> = userDao.getAll()

    suspend fun deleteUserById(userId: Int) = userDao.deleteUser(userId)
}