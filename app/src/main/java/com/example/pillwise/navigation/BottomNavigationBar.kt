package com.example.pillwise.navigation

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
import androidx.navigation.NavDestination.Companion.hasRoute
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

    val routes = listOf(
        PillWiseTopLevelRoute(R.string.home, Icons.Default.Home, HomeRoute),
        PillWiseTopLevelRoute(R.string.login, Icons.Default.Lock, LoginRoute),
        PillWiseTopLevelRoute(R.string.list, Icons.AutoMirrored.Filled.List, ListRoute),
    )

    NavigationBar {
        routes.forEach { topLevelRoute ->
            NavigationBarItem(
                icon = {
                    Icon(
                        topLevelRoute.icon,
                        contentDescription = stringResource(topLevelRoute.name)
                    )
                },
                label = { if (!isMobile) Text(stringResource(topLevelRoute.name)) else null },
                selected = currentDestination?.hierarchy?.any {
                    it.hasRoute(
                        topLevelRoute.route::class
                    )
                } == true,
                onClick = {
                    navController.navigate(topLevelRoute.route) {
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
