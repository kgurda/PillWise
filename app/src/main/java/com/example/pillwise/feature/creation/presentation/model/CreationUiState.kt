package com.example.pillwise.feature.creation.presentation.model

import android.graphics.Bitmap

data class CreationUiState(
    var name: String = "",
    var isNameValid: Boolean = true,
    var expirationDate: String = "",
    var isExpirationDateValid: Boolean = true,
    var comment: String = "",
    var photoUri: String? = null,
    var isLoading: Boolean = false,
    var capturedImage: Bitmap? = null,
    var created: Boolean = false
)