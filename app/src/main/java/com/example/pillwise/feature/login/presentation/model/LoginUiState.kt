package com.example.pillwise.feature.login.presentation.model

// here you can structure this however you want - it depends if you like using sealed classes, but it limits usage of copy, so it's your call :)
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loggedIn: Boolean = false,
    val error: String? = null,
)
