package com.fakher.multimodule.mvvm.database

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {

    @Provides
    @Singleton
    fun providesMainDatabase(context: Context): MainDatabase {
        return MainDatabase(context)
    }

    @Provides
    @Singleton
    fun provideTaskDao(mainDatabase: MainDatabase): TaskDao {
        return mainDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(mainDatabase: MainDatabase): UserDao {
        return mainDatabase.userDao()
    }
}