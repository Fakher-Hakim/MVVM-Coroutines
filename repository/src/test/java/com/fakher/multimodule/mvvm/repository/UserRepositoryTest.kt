package com.fakher.multimodule.mvvm.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fakher.multimodule.mvvm.database.User
import com.fakher.multimodule.mvvm.database.UserDao
import com.fakher.multimodule.mvvm.utils.MainCoroutineScopeRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    private lateinit var userDao: UserDao

    private lateinit var userRepository: UserRepository

    private val user = User(1, "username", "password".hashCode(), "info")

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        userRepository = UserRepository(userDao)
    }

    @Test
    fun should_get_user_by_id() {
        runBlockingTest {
            coEvery { userRepository.getUserByName("username") } returns user
            val result = userRepository.getUserByName("username")

            Assert.assertEquals(result, user)
            coVerify { userDao.getUser("username") }
        }
    }

    @Test
    fun should_get_user_by_id_not_found() {
        runBlockingTest {
            coEvery { userRepository.getUserByName(any()) } returns null
            val result = userRepository.getUserByName("username")

            Assert.assertNull(result)
            coVerify { userDao.getUser("username") }
        }
    }

    @Test
    fun should_get_all_users_success() {
        runBlockingTest {
            coEvery { userRepository.getAll() } returns listOf(user)
            val result = userRepository.getAll()

            Assert.assertEquals(result, listOf(user))
            coVerify { userDao.getAll() }
        }
    }

    @Test
    fun should_delete_user_success() {
        runBlockingTest {
            coEvery { userRepository.deleteUserById(any()) } returns Unit
            userRepository.deleteUserById(1)

            coVerify { userDao.deleteUser(1) }
        }
    }

    @Test
    fun should_insert_user_success() {
        runBlockingTest {
            coEvery { userRepository.insertUser(user) } returns 1
            val result = userRepository.insertUser(user)

            Assert.assertEquals(result, 1)
            coVerify { userDao.insertUser(user) }
        }
    }
}