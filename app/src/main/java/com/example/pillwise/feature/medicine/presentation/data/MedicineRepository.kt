package com.example.pillwise.feature.medicine.presentation.data

import com.example.pillwise.data.local.dao.MedicineDao
import com.example.pillwise.data.local.entities.Medicine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MedicineRepository {
    suspend fun create(medicine: Medicine)

    fun getAll(): Flow<List<Medicine>>
}

internal class MedicineRepositoryImpl
    @Inject
    constructor(
        private val medicineDao: MedicineDao,
    ) : MedicineRepository {
        override suspend fun create(medicine: Medicine) {
            medicineDao.insert(medicine)
        }

        override fun getAll() = medicineDao.getAll()
    }
