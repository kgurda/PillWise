package com.example.pillwise.login.data

import com.example.pillwise.feature.login.data.LoginRepository
import com.example.pillwise.feature.login.data.LoginRepositoryImpl
import org.junit.Before
import org.junit.Test

class LoginRepositoryImplTest {
    private lateinit var repository: LoginRepository

    @Before
    fun setUp() {
        repository = LoginRepositoryImpl()
    }

    @Test
    fun `should successfully login`() {
        // When
        val result = repository.login("admin", "admin")

        // Then
        assert(result.isSuccess)
    }

    @Test
    fun `should throw exception for invalid credentials`() {
        // When
        val result = repository.login("wrong-username", "wrong-password")

        // Then
        assert(result.isFailure)
        assert(result.exceptionOrNull()?.message == "Invalid username or password")
    }
}
