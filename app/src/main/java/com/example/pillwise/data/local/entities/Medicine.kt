package com.example.pillwise.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val expirationDate: String,
    val comment: String?,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray?
)
