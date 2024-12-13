package com.example.pillwise.feature.creation.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillwise.feature.creation.presentation.model.CreationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreationViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreationUiState())
    val uiState: StateFlow<CreationUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(
            name = name,
            isNameValid = true,
            isLoading = false
        )
    }

    fun updateExpirationDate(date: String) {
        _uiState.value = _uiState.value.copy(
            expirationDate = date,
            isExpirationDateValid = true,
            isLoading = false
        )
    }

    fun updateComment(comment: String) {
        _uiState.value = _uiState.value.copy(
            comment = comment,
            isLoading = false
        )
    }

    fun uploadPhoto(image: Bitmap) {
        _uiState.value = _uiState.value.copy(capturedImage = image)
    }

    fun create(): Boolean {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        val currentState = _uiState.value
        val isNameValid = currentState.name.isNotBlank()
        val isExpirationDateValid = currentState.expirationDate.isNotBlank()

        _uiState.value = currentState.copy(
            isNameValid = isNameValid,
            isExpirationDateValid = isExpirationDateValid,
            isLoading = false,
            created = isNameValid && isExpirationDateValid
        )

        return isNameValid && isExpirationDateValid
    }

    fun consumeCreatedAction() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                created = false
            )
        }
    }

}