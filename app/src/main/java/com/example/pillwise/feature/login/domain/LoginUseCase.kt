package com.example.pillwise.feature.login.domain

import com.example.pillwise.feature.login.data.LoginRepository
import javax.inject.Inject

// you can definitely omit domain layer for this project.
// You can also create domain models to communicate between presentation and data layers without them knowing of each other
class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    fun execute(username: String, password: String) = loginRepository.login(username, password)
}