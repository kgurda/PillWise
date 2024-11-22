package com.example.pillwise.login.data

import com.example.pillwise.feature.login.data.LoginRepositoryImpl
import org.junit.Test

class LoginRepositoryImplTest {

    @Test
    fun `should successfully login`() {
        val repository = LoginRepositoryImpl()

        val result = repository.login("admin", "admin")

        assert(result.isSuccess)
    }

    @Test
    fun `should throw exception for invalid credentials`() {
        val repository = LoginRepositoryImpl()

        val result = repository.login("wrong-username", "wrong-password")

        assert(result.isFailure)
        assert(result.exceptionOrNull()?.message == "Invalid username or password")
    }

}