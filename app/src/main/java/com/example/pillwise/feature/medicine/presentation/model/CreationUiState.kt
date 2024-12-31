package com.example.pillwise.feature.medicine.presentation.model

import android.graphics.Bitmap
import com.example.pillwise.data.local.entities.Medicine

data class CreationUiState(
    var name: String = "",
    var isNameValid: Boolean = true,
    var expirationDate: String = "",
    var isExpirationDateValid: Boolean = true,
    var comment: String = "",
    var isLoading: Boolean = false,
    var capturedImage: Bitmap? = null,
    var created: Boolean = false,
    var medicines: List<Medicine> = emptyList<Medicine>(),
)
