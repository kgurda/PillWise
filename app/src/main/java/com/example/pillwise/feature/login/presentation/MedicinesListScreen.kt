package com.example.pillwise.feature.login.presentation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MedicinesListScreen() {
    MedicinesListScreen()
}

@Composable
private fun MedicinesListScreen(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "List view",
        style = MaterialTheme.typography.headlineMedium,
    )
}

@Preview
@Composable
private fun MedicinesListScreenPreview() {
    MedicinesListScreen()
}
