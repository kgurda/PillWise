package com.example.pillwise.feature.medicine.list.presentation.data.di

import com.example.pillwise.feature.medicine.list.presentation.data.MedicineListRepository
import com.example.pillwise.feature.medicine.list.presentation.data.MedicineListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MedicineListModule {
    @Binds
    internal abstract fun bindMedicineListRepository(impl: MedicineListRepositoryImpl): MedicineListRepository
}
