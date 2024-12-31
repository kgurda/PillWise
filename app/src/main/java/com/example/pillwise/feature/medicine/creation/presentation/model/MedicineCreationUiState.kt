package com.example.pillwise.feature.medicine.creation.presentation.model

import android.graphics.Bitmap

data class MedicineCreationUiState(
    var name: String = "",
    var expirationDate: String = "",
    var comment: String = "",
    var isLoading: Boolean = false,
    var capturedImage: Bitmap? = null,
    var created: Boolean = false
)

data class MedicineCreationValidationState(
    var isNameValid: Boolean = true,
    var isExpirationDateValid: Boolean = true
)
