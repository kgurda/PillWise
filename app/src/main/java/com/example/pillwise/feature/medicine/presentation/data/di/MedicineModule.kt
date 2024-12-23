package com.example.pillwise.feature.medicine.presentation.data.di

import com.example.pillwise.feature.medicine.presentation.data.MedicineRepository
import com.example.pillwise.feature.medicine.presentation.data.MedicineRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MedicineModule {

    @Binds
    internal abstract fun bindMedicineRepository(impl: MedicineRepositoryImpl) : MedicineRepository

}