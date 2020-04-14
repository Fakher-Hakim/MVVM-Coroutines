package com.fakher.multimodule.mvvm.usecase.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fakher.multimodule.mvvm.database.User
import com.fakher.multimodule.mvvm.repository.UserRepository
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
class SignupViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    lateinit var userRepository: UserRepository

    private val user = User(0, "aze", "aze".hashCode(), "info")

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun should_create_new_user() {
        val viewModel = SignupViewModel(userRepository)
        coEvery { userRepository.getUserByName("aze") } returns null
        coEvery { userRepository.insertUser(user) } returns 5
        val newUser = user.copy(id = 5)

        viewModel.signup("aze", "aze", "info")

        Assert.assertNull(viewModel.error.value)
        Assert.assertEquals(viewModel.newUser.value, newUser)
    }

    @Test
    fun should_fail_create_new_user_exists() {
        val viewModel = SignupViewModel(userRepository)
        coEvery { userRepository.getUserByName("aze") } returns user

        viewModel.signup("aze", "aze", "info")

        Assert.assertEquals(viewModel.error.value, "User already exists!")
        Assert.assertNull(viewModel.newUser.value)
    }

    @Test
    fun should_fail_create_new_user_invalid_input() {
        val viewModel = SignupViewModel(userRepository)
        coEvery { userRepository.getUserByName("aze") } returns user

        viewModel.signup("", "", "")

        Assert.assertEquals(viewModel.error.value, "Please fill all fields!")
        Assert.assertNull(viewModel.newUser.value)
    }
}