package com.example.pillwise.feature.medicine.list.presentation.model

data class MedicineListUiState(
    var medicines: List<MedicineUiState> = emptyList<MedicineUiState>()
)

data class MedicineUiState(
    var id: Long,
    var name: String,
    var expirationDate: String,
    var comment: String?,
    var image: ByteArray?,
    var isMarkedForDeletion: Boolean = false
)
