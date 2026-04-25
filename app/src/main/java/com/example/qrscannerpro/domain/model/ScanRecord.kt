package com.example.qrscannerpro.domain.model

import java.time.Instant

data class ScanRecord(
    val id: Long = 0,
    val rawValue: String,
    val type: ScanType,
    val timestamp: Instant = Instant.now(),
    val favorite: Boolean = false,
)
