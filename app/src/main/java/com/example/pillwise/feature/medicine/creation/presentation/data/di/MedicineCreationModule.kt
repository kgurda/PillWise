package com.example.pillwise.feature.medicine.creation.presentation.data.di

import com.example.pillwise.feature.medicine.creation.presentation.data.MedicineCreationRepository
import com.example.pillwise.feature.medicine.creation.presentation.data.MedicineCreationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MedicineCreationModule {
    @Binds
    internal abstract fun bindMedicineCreationRepository(impl: MedicineCreationRepositoryImpl): MedicineCreationRepository
}
