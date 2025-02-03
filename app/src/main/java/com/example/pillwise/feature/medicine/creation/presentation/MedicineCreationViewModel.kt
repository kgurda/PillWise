package com.example.pillwise.feature.medicine.creation.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillwise.data.local.entities.Medicine
import com.example.pillwise.feature.medicine.creation.presentation.data.MedicineCreationRepository
import com.example.pillwise.feature.medicine.creation.presentation.model.MedicineCreationUiState
import com.example.pillwise.feature.medicine.creation.presentation.model.MedicineCreationValidationState
import com.example.pillwise.feature.medicine.presentation.BitmapConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineCreationViewModel
    @Inject
    constructor(
        private val medicineRepository: MedicineCreationRepository
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MedicineCreationUiState())
        private val _validationState = MutableStateFlow(MedicineCreationValidationState())

        val uiState: StateFlow<MedicineCreationUiState> = _uiState.asStateFlow()
        val validationState: StateFlow<MedicineCreationValidationState> = _validationState.asStateFlow()

        fun updateName(name: String) {
            _uiState.update { it.copy(name = name, isLoading = false) }
            _validationState.update { it.copy(isNameValid = name.isNotEmpty()) }
        }

        fun updateExpirationDate(date: String) {
            _uiState.update { it.copy(expirationDate = date, isLoading = false) }
            _validationState.update { it.copy(isExpirationDateValid = date.isNotEmpty()) }
        }

        fun updateComment(comment: String) {
            _uiState.update { it.copy(comment = comment, isLoading = false) }
        }

        fun uploadPhoto(image: Bitmap) {
            _uiState.update { it.copy(capturedImage = image) }
        }

        fun create() =
            viewModelScope.launch {
                val currentState = _uiState.updateAndGet { it.copy(isLoading = true) }

                val isNameValid = currentState.name.isNotEmpty()
                val isExpirationDateValid = currentState.expirationDate.isNotEmpty()

                _validationState.update {
                    it.copy(
                        isNameValid = isNameValid,
                        isExpirationDateValid = isExpirationDateValid
                    )
                }

                if (isNameValid && isExpirationDateValid) {
                    val image =
                        currentState.capturedImage?.let { bitmap ->
                            BitmapConverter.convertBitmapToString(bitmap)
                        }
                    medicineRepository.create(
                        Medicine(
                            name = currentState.name,
                            expirationDate = currentState.expirationDate,
                            comment = currentState.comment,
                            image = image
                        )
                    )
                }

                _uiState.update { it.copy(isLoading = false, created = isNameValid && isExpirationDateValid) }
            }

        fun consumeCreatedAction() =
            viewModelScope.launch {
                _uiState.update { it.copy(created = false) }
            }
    }
