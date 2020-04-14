package com.fakher.multimodule.mvvm.repository

import com.fakher.multimodule.mvvm.database.TaskDao
import com.fakher.multimodule.mvvm.database.UserDao
import com.fakher.multimodule.mvvm.network.NetworkApi
import com.fakher.multimodule.mvvm.network.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao, networkApi: NetworkApi): TaskRepository {
        return TaskRepository(taskDao, networkApi)
    }
}