package com.example.pillwise.feature.login.data

import com.example.pillwise.feature.login.data.model.LoginResultEntity
import javax.inject.Inject

interface LoginRepository {

    // I used Result here just for simplicity, you can replace it with whatever
    fun login(): Result<LoginResultEntity>

}

// You can place this in another file, it doesn't matter
internal class LoginRepositoryImpl @Inject constructor() : LoginRepository {

    override fun login(): Result<LoginResultEntity> {
        return Result.success(LoginResultEntity("something"))
    }

}
