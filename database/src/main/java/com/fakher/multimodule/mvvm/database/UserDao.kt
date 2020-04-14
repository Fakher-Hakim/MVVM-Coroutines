package com.fakher.multimodule.mvvm.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUser(username: String): User?

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id: Int)

}