package com.example.pillwise.feature.login.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pillwise.feature.login.presentation.model.LoginUiState

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LoginScreen(
        uiState = uiState,
    )
}

@Composable
private fun LoginScreen(
    uiState: LoginUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = "Build anything!",
        )
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(LoginUiState())
}
