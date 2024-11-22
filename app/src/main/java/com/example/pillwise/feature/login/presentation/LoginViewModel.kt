package com.example.pillwise.feature.login.presentation

import androidx.lifecycle.ViewModel
import com.example.pillwise.feature.login.domain.LoginUseCase
import com.example.pillwise.feature.login.presentation.model.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        _uiState.update {
            it.copy(
                isLoading = true,
                loggedIn = false,
                error = null,
            )
        }

        loginUseCase.execute(username, password)
            .onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loggedIn = true,
                    )
                }
            }
            .onFailure { result ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "anything",
                    )
                }
            }
    }

}