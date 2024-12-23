package com.example.pillwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pillwise.feature.home.presentation.HomeScreen
import com.example.pillwise.feature.login.presentation.LoginScreen
import com.example.pillwise.feature.login.presentation.LoginViewModel
import com.example.pillwise.feature.medicine.presentation.CreationScreen
import com.example.pillwise.feature.medicine.presentation.ListScreen
import com.example.pillwise.feature.medicine.presentation.MedicineViewModel
import com.example.pillwise.navigation.BottomNavigationBar
import com.example.pillwise.navigation.routes.CreationRoute
import com.example.pillwise.navigation.routes.HomeRoute
import com.example.pillwise.navigation.routes.ListRoute
import com.example.pillwise.navigation.routes.LoginRoute
import com.example.pillwise.ui.theme.PillWiseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PillWiseTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                navController = navController,
                                currentDestination = currentDestination,
                            )
                        },
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = HomeRoute,
                            modifier = Modifier.padding(paddingValues = paddingValues),
                        ) {
                            composable<HomeRoute> {
                                HomeScreen(navController)
                            }
                            composable<ListRoute> {
                                val creationViewModel = hiltViewModel<MedicineViewModel>()
                                ListScreen(navController, creationViewModel)
                            }
                            composable<LoginRoute> {
                                val viewModel = hiltViewModel<LoginViewModel>()
                                LoginScreen(navController, viewModel)
                            }
                            composable<CreationRoute> {
                                val creationViewModel = hiltViewModel<MedicineViewModel>()
                                CreationScreen(navController, creationViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
