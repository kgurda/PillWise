package com.example.pillwise.feature.medicine.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillwise.feature.medicine.list.presentation.data.MedicineListRepository
import com.example.pillwise.feature.medicine.list.presentation.model.MedicineListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MedicineListViewModel
    @Inject
    constructor(
        medicineRepository: MedicineListRepository
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MedicineListUiState())
        val uiState: StateFlow<MedicineListUiState> = _uiState.asStateFlow()

        init {
            medicineRepository.getAll()
                .onEach { medicines ->
                    _uiState.update { it.copy(medicines = medicines) }
                }
                .launchIn(viewModelScope)
        }
    }
