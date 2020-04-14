package com.fakher.multimodule.mvvm.repository

import com.fakher.multimodule.mvvm.database.User
import com.fakher.multimodule.mvvm.database.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(newUser: User) = userDao.insertUser(newUser)

    suspend fun getUserByName(username: String) = userDao.getUser(username)

    suspend fun getAll(): List<User> = userDao.getAll()

    suspend fun deleteUserById(userId: Int) = userDao.deleteUser(userId)
}