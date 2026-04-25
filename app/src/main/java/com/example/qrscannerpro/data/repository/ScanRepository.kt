package com.example.qrscannerpro.data.repository

import com.example.qrscannerpro.domain.model.ScanRecord
import kotlinx.coroutines.flow.Flow

interface ScanRepository {
    fun observeHistory(): Flow<List<ScanRecord>>
    suspend fun save(record: ScanRecord)
    suspend fun toggleFavorite(id: Long)
    suspend fun clearAll()
}
