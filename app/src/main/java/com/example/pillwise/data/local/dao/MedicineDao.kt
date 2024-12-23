package com.example.pillwise.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pillwise.data.local.entities.Medicine

@Dao
interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicine: Medicine)

    @Query("SELECT * FROM medicines")
    fun getAll(): List<Medicine>

    @Query("DELETE FROM medicines")
    suspend fun clear()
}
