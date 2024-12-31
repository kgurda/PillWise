package com.example.pillwise.feature.login.domain

import com.example.pillwise.feature.login.data.LoginRepository
import javax.inject.Inject

class LoginUseCase
    @Inject
    constructor(
        private val loginRepository: LoginRepository
    ) {
        fun execute(
            username: String,
            password: String
        ) = loginRepository.login(username, password)
    }
