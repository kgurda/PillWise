package com.example.pillwise.feature.medicine.creation.presentation.data

import com.example.pillwise.data.local.dao.MedicineDao
import com.example.pillwise.data.local.entities.Medicine
import javax.inject.Inject

interface MedicineCreationRepository {
    suspend fun create(medicine: Medicine)
}

internal class MedicineCreationRepositoryImpl
    @Inject
    constructor(
        private val medicineDao: MedicineDao,
    ) : MedicineCreationRepository {
        override suspend fun create(medicine: Medicine) {
            medicineDao.insert(medicine)
        }
    }
