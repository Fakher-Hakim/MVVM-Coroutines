package com.fakher.multimodule.mvvm.usecase.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fakher.multimodule.mvvm.database.User
import com.fakher.multimodule.mvvm.repository.UserRepository
import com.fakher.multimodule.mvvm.usecase.signup.SignupViewModel
import com.fakher.multimodule.mvvm.utils.MainCoroutineScopeRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    lateinit var userRepository: UserRepository

    private val user = User(1, "aze", "aze".hashCode(), "info")

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun should_login_success() {
        val viewModel = LoginViewModel(userRepository)
        coEvery { userRepository.getUserByName("aze") } returns user

        viewModel.login("aze", "aze")

        Assert.assertNull(viewModel.error.value)
        Assert.assertEquals(viewModel.loggedUser.value, user)
    }

    @Test
    fun should_login_fail_not_input() {
        val viewModel = LoginViewModel(userRepository)
        coEvery { userRepository.getUserByName(any()) } returns user

        viewModel.login("", "")

        Assert.assertEquals(viewModel.error.value, "Please fill all fields!")
        Assert.assertNull(viewModel.loggedUser.value)
    }

    @Test
    fun should_login_fail_not_user() {
        val viewModel = LoginViewModel(userRepository)
        coEvery { userRepository.getUserByName(any()) } returns null

        viewModel.login("fake", "fake")

        Assert.assertEquals(viewModel.error.value, "User not found!")
        Assert.assertNull(viewModel.loggedUser.value)
    }

    @Test
    fun should_login_fail_wrong_password() {
        val viewModel = LoginViewModel(userRepository)
        coEvery { userRepository.getUserByName(any()) } returns user

        viewModel.login("fake", "fake")

        Assert.assertEquals(viewModel.error.value, "Password is wrong!")
        Assert.assertNull(viewModel.loggedUser.value)
    }
}