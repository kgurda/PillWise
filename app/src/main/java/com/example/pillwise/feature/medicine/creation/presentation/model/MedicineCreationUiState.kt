package com.example.pillwise.feature.medicine.creation.presentation.model

import android.graphics.Bitmap

data class MedicineCreationUiState(
    var name: String = "",
    var isNameValid: Boolean = true,
    var expirationDate: String = "",
    var isExpirationDateValid: Boolean = true,
    var comment: String = "",
    var isLoading: Boolean = false,
    var capturedImage: Bitmap? = null,
    var created: Boolean = false,
)
