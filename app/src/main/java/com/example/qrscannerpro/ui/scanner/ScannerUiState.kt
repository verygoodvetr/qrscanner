package com.example.qrscannerpro.ui.scanner

import com.example.qrscannerpro.domain.model.ScanType

data class ScannerUiState(
    val lastDetected: String? = null,
    val latestType: ScanType = ScanType.UNKNOWN,
    val batchMode: Boolean = false,
)
