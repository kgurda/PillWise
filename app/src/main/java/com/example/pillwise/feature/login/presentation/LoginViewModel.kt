package com.example.pillwise.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pillwise.feature.login.domain.LoginUseCase
import com.example.pillwise.feature.login.presentation.model.LoginUiState
import com.example.pillwise.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
//    private val navController: NavController,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun setUsername(username: String) {
        _uiState.update {
            it.copy(
                username = username,
                error = null
            )
        }
    }

    fun setPassword(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                error = null
            )
        }
    }

    fun login() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                isLoading = true,
                loggedIn = false,
                error = null,
            )
        }

        loginUseCase.execute(_uiState.value.username, _uiState.value.password)
            .onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loggedIn = true,
                    )
                }
//                navController.navigate(Screens.LIST.route)
            }
            .onFailure { result ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = result.message,
                    )
                }
            }
    }
}