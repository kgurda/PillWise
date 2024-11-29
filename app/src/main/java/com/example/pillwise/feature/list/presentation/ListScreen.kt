package com.example.pillwise.feature.list.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ListScreen(modifier: Modifier = Modifier) {
    Text(
        fontSize = 14.sp,
        text = "List screen",
    )
}

@Preview
@Composable
private fun ListScreenPreview() {
    ListScreen()
}