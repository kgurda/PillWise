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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.pillwise.R
import com.example.pillwise.navigation.routes.HomeRoute
import com.example.pillwise.navigation.routes.LoginRoute
import com.example.pillwise.navigation.routes.MedicineCreationRoute
import com.example.pillwise.navigation.routes.MedicineListRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentDestination: NavDestination?
) {
    val configuration = LocalConfiguration.current
    val isMobile = configuration.screenWidthDp < 600

    val routes =
        listOf(
            PillWiseTopLevelRoute(R.string.home, Icons.Default.Home, HomeRoute),
            PillWiseTopLevelRoute(R.string.login, Icons.Default.Lock, LoginRoute),
            PillWiseTopLevelRoute(R.string.list, Icons.AutoMirrored.Filled.List, MedicineListRoute)
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
                selected =
                    currentDestination?.hierarchy?.any {
                        it.hasRoute(
                            topLevelRoute.route::class
                        )
                    } == true || (
                        currentDestination?.route == MedicineCreationRoute.javaClass.name &&
                            topLevelRoute.route == MedicineListRoute
                    ),
                onClick = {
                    navController.navigate(topLevelRoute.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
