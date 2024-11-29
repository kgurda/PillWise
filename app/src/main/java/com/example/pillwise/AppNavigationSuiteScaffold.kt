package com.example.pillwise

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.pillwise.feature.home.presentation.HomeScreen
import com.example.pillwise.feature.list.presentation.ListScreen
import com.example.pillwise.feature.login.presentation.LoginScreen

@Composable
fun AppNavigationSuiteScaffold() {
    var currentScreen by rememberSaveable { mutableStateOf(Screen.HOME) }
    val configuration = LocalConfiguration.current
    val isMobile = configuration.screenWidthDp < 600

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            Screen.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = stringResource(it.label)
                        )
                    },
                    label = { if (!isMobile) Text(stringResource(it.label)) else null },
                    selected = it == currentScreen,
                    onClick = { currentScreen = it }
                )
            }
        }
    ) {
        when (currentScreen) {
            Screen.HOME -> HomeScreen(
                onNavigateToLogin = { currentScreen = Screen.LOGIN }
            )
            Screen.LIST -> ListScreen()
            Screen.LOGIN -> LoginScreen(
                onLoginSuccess = { currentScreen = Screen.LIST }
            )
        }
    }
}

enum class Screen(
    @StringRes val label: Int,
    val icon: ImageVector
) {
    HOME(R.string.home, Icons.Default.Home),
    LOGIN(R.string.login, Icons.Default.Lock),
    LIST(R.string.list, Icons.AutoMirrored.Filled.List),
}

