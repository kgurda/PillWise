package com.example.pillwise.feature.login.data

import com.example.pillwise.feature.login.data.model.LoginResultEntity
import javax.inject.Inject

interface LoginRepository {

    // I used Result here just for simplicity, you can replace it with whatever
    fun login(username: String, password: String): Result<LoginResultEntity>

}

// You can place this in another file, it doesn't matter
internal class LoginRepositoryImpl @Inject constructor() : LoginRepository {

    override fun login(username: String, password: String): Result<LoginResultEntity> {
        // it will be changed in the future with connection to DB or api call
        val map = mapOf<String, String>(
            "admin" to "admin"
        )
        val shouldLogin = map[username] == password
        return if (shouldLogin) {
            Result.success(LoginResultEntity("something"))
        } else {
            Result.failure(InvalidCredentialsException())
        }
    }

}

class InvalidCredentialsException: RuntimeException("Invalid username or password")