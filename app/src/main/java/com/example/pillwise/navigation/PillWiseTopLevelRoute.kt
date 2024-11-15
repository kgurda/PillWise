package com.example.pillwise.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class PillWiseTopLevelRoute<T: Any>(
    val name: Int,
    val icon: ImageVector,
    val route: T,
)
