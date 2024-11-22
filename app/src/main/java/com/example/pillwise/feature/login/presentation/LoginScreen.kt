package com.example.pillwise.feature.login.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pillwise.feature.login.presentation.model.LoginUiState
import com.example.pillwise.ui.theme.PurpleGrey40

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LoginScreen(
        uiState = uiState,
        onLoginClick = { username, password -> viewModel.login(username, password) },
    )
}

@Composable
private fun LoginScreen(
    uiState: LoginUiState,
    onLoginClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize().padding(16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = { onLoginClick(username, password) },
                modifier = Modifier.fillMaxWidth(),
                enabled = username.isNotEmpty() && password.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = PurpleGrey40
                )
            ) {
                Text("Login")
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            if (!uiState.error.isNullOrEmpty()) {
                Text(
                    fontSize = 14.sp,
                    text = "Invalid username or password",
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
        onLoginClick = { _, _ -> },
    )
}
