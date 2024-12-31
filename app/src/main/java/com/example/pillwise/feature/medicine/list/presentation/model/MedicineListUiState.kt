package com.example.pillwise.feature.medicine.list.presentation.model

import com.example.pillwise.data.local.entities.Medicine

data class MedicineListUiState(
    var medicines: List<Medicine> = emptyList<Medicine>(),
)
