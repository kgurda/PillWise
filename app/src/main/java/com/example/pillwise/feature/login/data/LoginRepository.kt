package com.example.pillwise.feature.login.data

import javax.inject.Inject

interface LoginRepository {

    fun login(username: String, password: String): Result<Unit>

}

internal class LoginRepositoryImpl @Inject constructor() : LoginRepository {

    override fun login(username: String, password: String): Result<Unit> {
        val map = mapOf<String, String>(
            "admin" to "admin"
        )
        val shouldLogin = map[username] == password
        return if (shouldLogin) {
            Result.success(Unit)
        } else {
            Result.failure(InvalidCredentialsException())
        }
    }

}

class InvalidCredentialsException: RuntimeException("Invalid username or password")