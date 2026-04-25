package com.example.qrscannerpro.data.repository

import com.example.qrscannerpro.data.local.ScanDao
import com.example.qrscannerpro.data.local.ScanEntity
import com.example.qrscannerpro.domain.model.ScanRecord
import com.example.qrscannerpro.domain.model.ScanType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

class ScanRepositoryImpl @Inject constructor(
    private val dao: ScanDao,
) : ScanRepository {

    override fun observeHistory(): Flow<List<ScanRecord>> = dao.observeAll().map { list ->
        list.map { entity ->
            ScanRecord(
                id = entity.id,
                rawValue = entity.rawValue,
                type = ScanType.valueOf(entity.type),
                timestamp = Instant.ofEpochMilli(entity.timestampEpochMillis),
                favorite = entity.favorite,
            )
        }
    }

    override suspend fun save(record: ScanRecord) {
        dao.insert(
            ScanEntity(
                id = record.id,
                rawValue = record.rawValue,
                type = record.type.name,
                timestampEpochMillis = record.timestamp.toEpochMilli(),
                favorite = record.favorite,
            )
        )
    }

    override suspend fun toggleFavorite(id: Long) = dao.toggleFavorite(id)

    override suspend fun clearAll() = dao.clearAll()
}
