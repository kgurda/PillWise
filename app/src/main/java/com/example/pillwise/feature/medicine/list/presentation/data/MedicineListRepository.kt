package com.example.pillwise.feature.medicine.list.presentation.data

import com.example.pillwise.data.local.dao.MedicineDao
import com.example.pillwise.data.local.entities.Medicine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MedicineListRepository {
    fun getAll(): Flow<List<Medicine>>
}

internal class MedicineListRepositoryImpl
    @Inject
    constructor(
        private val medicineDao: MedicineDao
    ) : MedicineListRepository {
        override fun getAll() = medicineDao.getAll()
    }
