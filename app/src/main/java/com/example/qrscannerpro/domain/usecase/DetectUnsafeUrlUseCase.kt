package com.example.qrscannerpro.domain.usecase

import java.net.URI
import javax.inject.Inject

class DetectUnsafeUrlUseCase @Inject constructor() {
    private val suspiciousHosts = listOf("bit.ly", "tinyurl.com", "t.co")

    operator fun invoke(url: String): Boolean {
        return try {
            val host = URI(url).host?.lowercase().orEmpty()
            host.isBlank() || suspiciousHosts.any { host.contains(it) }
        } catch (_: Exception) {
            true
        }
    }
}
