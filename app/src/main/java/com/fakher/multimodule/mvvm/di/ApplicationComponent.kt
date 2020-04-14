package com.fakher.multimodule.mvvm.di

import com.fakher.multimodule.mvvm.database.DaoModule
import com.fakher.multimodule.mvvm.repository.RepositoryModule
import com.fakher.multimodule.mvvm.usecase.login.LoginViewModel
import com.fakher.multimodule.mvvm.usecase.main.MainViewModel
import com.fakher.multimodule.mvvm.usecase.signup.SignupViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class, DaoModule::class])
interface ApplicationComponent {

    fun getLoginVMFactory(): ViewModelFactory<LoginViewModel>
    fun getMainVMFactory(): ViewModelFactory<MainViewModel>
    fun getSignupVMFactory(): ViewModelFactory<SignupViewModel>
}