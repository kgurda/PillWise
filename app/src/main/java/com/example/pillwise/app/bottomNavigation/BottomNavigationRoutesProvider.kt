package com.example.pillwise.app.bottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import com.example.pillwise.R
import com.example.pillwise.feature.add.presentation.navigation.AddRoute
import com.example.pillwise.feature.browse.presentation.navigation.BrowseRoute
import com.example.pillwise.feature.login.presentation.navigation.LoginRoute
import com.example.pillwise.navigation.PillWiseTopLevelRoute
import javax.inject.Inject

class BottomNavigationRoutesProvider @Inject constructor() {

    fun provide() = listOf(
        PillWiseTopLevelRoute(R.string.bottom_bar_login_text, Icons.Filled.Call, LoginRoute),
        PillWiseTopLevelRoute(R.string.bottom_bar_browse_text, Icons.Filled.Favorite, BrowseRoute),
        PillWiseTopLevelRoute(R.string.bottom_bar_add_text, Icons.Filled.AddCircle, AddRoute),
    )

}