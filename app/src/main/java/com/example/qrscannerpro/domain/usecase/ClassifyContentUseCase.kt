package com.example.qrscannerpro.domain.usecase

import android.util.Patterns
import com.example.qrscannerpro.domain.model.ScanType
import javax.inject.Inject

class ClassifyContentUseCase @Inject constructor() {

    operator fun invoke(content: String): ScanType {
        val text = content.trim()
        return when {
            text.startsWith("WIFI:", ignoreCase = true) -> ScanType.WIFI
            text.startsWith("BEGIN:VCARD", ignoreCase = true) -> ScanType.CONTACT
            text.startsWith("mailto:", ignoreCase = true) -> ScanType.EMAIL
            text.startsWith("tel:", ignoreCase = true) -> ScanType.PHONE
            text.startsWith("geo:", ignoreCase = true) -> ScanType.LOCATION
            Patterns.WEB_URL.matcher(text).matches() -> ScanType.URL
            text.all { it.isDigit() } && text.length in 8..14 -> ScanType.BARCODE
            text.isNotEmpty() -> ScanType.TEXT
            else -> ScanType.UNKNOWN
        }
    }
}
