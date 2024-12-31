package com.example.pillwise.feature.medicine.presentation

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar

@Composable
fun DatePicker(
    onDateSelected: (String) -> Unit,
    shouldShowDataPicker: Boolean = false,
    onDismiss: () -> Unit,
) {
    // TODO remove onDismiss and showDatePicker
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val context = LocalContext.current

    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(shouldShowDataPicker) {
        showDatePicker = shouldShowDataPicker
    }

    if (showDatePicker) {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSelected(selectedDate)
                showDatePicker = false
            },
            year,
            month,
            day,
        ).apply {
            setOnDismissListener {
                onDismiss()
                showDatePicker = false
            }
            show()
        }
    }
}

@Preview
@Composable
private fun DatePickerPreview() {
    DatePicker(
        onDateSelected = {},
        shouldShowDataPicker = true,
        onDismiss = {},
    )
}
