package com.example.pillwise.feature.medicine.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillwise.feature.medicine.list.presentation.data.MedicineListRepository
import com.example.pillwise.feature.medicine.list.presentation.model.MedicineListUiState
import com.example.pillwise.feature.medicine.list.presentation.model.MedicineUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineListViewModel
    @Inject
    constructor(
        private val medicineRepository: MedicineListRepository
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MedicineListUiState())
        val uiState: StateFlow<MedicineListUiState> = _uiState.asStateFlow()

        init {
            medicineRepository.getAll().onEach { medicines ->
                _uiState.update {
                    it.copy(
                        medicines =
                            medicines.map { medicine ->
                                MedicineUiState(
                                    id = medicine.id,
                                    name = medicine.name,
                                    expirationDate = medicine.expirationDate,
                                    comment = medicine.comment,
                                    image = medicine.image
                                )
                            }
                    )
                }
            }.launchIn(viewModelScope)
        }

        fun deleteItem(id: Long) =
            viewModelScope.launch {
                medicineRepository.delete(id)
                _uiState.update { currentState ->
                    currentState.copy(
                        medicines = currentState.medicines.filter { it.id != id }
                    )
                }
            }

        fun markItemForDeletion(
            id: Long,
            isMarked: Boolean
        ) {
            _uiState.update { currentState ->
                currentState.copy(
                    medicines =
                        currentState.medicines.map { medicine ->
                            if (medicine.id == id) {
                                medicine.copy(isMarkedForDeletion = isMarked)
                            } else {
                                medicine
                            }
                        }
                )
            }
        }
    }
