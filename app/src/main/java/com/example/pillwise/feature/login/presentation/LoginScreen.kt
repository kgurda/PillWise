package com.example.pillwise.feature.login.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import com.example.pillwise.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pillwise.feature.login.presentation.model.LoginUiState

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LoginScreen(
        uiState = uiState,
        onUsernameChange = { username -> viewModel.setUsername(username)},
        onPasswordChange = { password -> viewModel.setPassword(password)},
        onLoginClick = { viewModel.login() },
    )
}

@Composable
private fun LoginScreen(
    uiState: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize().padding(16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.login_page_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            TextField(
                value = uiState.username,
                onValueChange = {
                    Log.d("test", it)
                    onUsernameChange(it)
                },
                label = { Text(stringResource(R.string.username_text_field)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            TextField(
                value = uiState.password,
                onValueChange = { onPasswordChange(it) },
                label = { Text(stringResource(R.string.password_text_field)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = { onLoginClick() },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.username.isNotEmpty() && uiState.password.isNotEmpty()
            ) {
                Text(stringResource(R.string.login_button_name))
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            if (!uiState.error.isNullOrEmpty()) {
                Text(
                    fontSize = 14.sp,
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(),
        onUsernameChange = { _ -> },
        onPasswordChange = { _ -> },
        onLoginClick = { -> },
    )
}
