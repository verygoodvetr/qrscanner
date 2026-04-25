package com.example.qrscannerpro.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_history")
data class ScanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val rawValue: String,
    val type: String,
    val timestampEpochMillis: Long,
    val favorite: Boolean,
)
