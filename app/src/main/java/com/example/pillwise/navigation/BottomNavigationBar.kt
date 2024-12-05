package com.example.pillwise.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.pillwise.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentDestination: NavDestination?
) {
    val configuration = LocalConfiguration.current
    val isMobile = configuration.screenWidthDp < 600

    NavigationBar {
        Screens.entries.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = stringResource(screen.label)
                    )
                },
                label = { if (!isMobile) Text(stringResource(screen.label)) else null },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

enum class Screens(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.home, Icons.Default.Home, "home_route"),
    LOGIN(R.string.login, Icons.Default.Lock, "login_route"),
    LIST(R.string.list, Icons.AutoMirrored.Filled.List, "list_route"),
}

