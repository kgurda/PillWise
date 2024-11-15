package com.example.pillwise.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pillwise.app.bottomNavigation.BottomNavigationRoutesProvider
import com.example.pillwise.feature.add.presentation.AddScreen
import com.example.pillwise.feature.add.presentation.navigation.AddRoute
import com.example.pillwise.feature.browse.presentation.BrowseScreen
import com.example.pillwise.feature.browse.presentation.navigation.BrowseRoute
import com.example.pillwise.feature.login.presentation.LoginScreen
import com.example.pillwise.feature.login.presentation.LoginViewModel
import com.example.pillwise.feature.login.presentation.navigation.LoginRoute
import com.example.pillwise.app.theme.PillWiseTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var bottomNavigationRoutesProvider: BottomNavigationRoutesProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PillWiseApp()
        }
    }

    @Composable
    fun PillWiseApp() {
        PillWiseTheme {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            NavigationSuiteScaffold(
                modifier = Modifier.safeDrawingPadding(),
                navigationSuiteItems = {
                    bottomNavigationRoutesProvider.provide().forEach { topLevelRoute ->
                        item(
                            selected = currentDestination?.hierarchy?.any {
                                it.hasRoute(
                                    topLevelRoute.route::class
                                )
                            } == true,
                            onClick = {
                                navController.navigate(topLevelRoute.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    topLevelRoute.icon,
                                    contentDescription = stringResource(topLevelRoute.name)
                                )
                            },
                            label = { Text(stringResource(topLevelRoute.name)) },
                        )
                    }
                },
                content = {
                    NavHost(
                        navController = navController,
                        startDestination = LoginRoute,
                    ) {
                        composable<LoginRoute> {
                            val viewModel = hiltViewModel<LoginViewModel>()
                            LoginScreen(viewModel)
                        }
                        composable<BrowseRoute> {
                            BrowseScreen()
                        }
                        composable<AddRoute> {
                            AddScreen()
                        }
                    }
                }
            )
        }
    }
}
