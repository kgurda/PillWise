package com.example.pillwise.di

import android.content.Context
import androidx.room.Room
import com.example.pillwise.data.local.dao.MedicineDao
import com.example.pillwise.data.local.db.PillWiseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun providePillWiseDatabase(@ApplicationContext context: Context): PillWiseDatabase {
        return Room.databaseBuilder(
            context,
            PillWiseDatabase::class.java,
            "pill_wise_database"
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideMedicineDao(pillWiseDatabase: PillWiseDatabase): MedicineDao {
        return pillWiseDatabase.medicineDao()
    }
}