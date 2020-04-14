package com.fakher.multimodule.mvvm.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(task:List<Task>)

    @Query("SELECT * FROM task WHERE user_id = :userId")
    suspend fun getTasksForUser(userId: Int): List<Task>?

    @Query("SELECT * FROM task WHERE id =:id")
    suspend fun getTaskById(id: Int): Task
}