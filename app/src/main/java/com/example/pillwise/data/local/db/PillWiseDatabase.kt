package com.example.pillwise.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pillwise.data.local.dao.MedicineDao
import com.example.pillwise.data.local.entities.Medicine

@Database(entities = [Medicine::class], version = 2)
abstract class PillWiseDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
}
