package com.example.qrscannerpro.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ScanEntity)

    @Query("SELECT * FROM scan_history ORDER BY timestampEpochMillis DESC")
    fun observeAll(): Flow<List<ScanEntity>>

    @Query("UPDATE scan_history SET favorite = NOT favorite WHERE id = :id")
    suspend fun toggleFavorite(id: Long)

    @Query("DELETE FROM scan_history")
    suspend fun clearAll()
}
