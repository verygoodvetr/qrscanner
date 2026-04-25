package com.example.qrscannerpro.ui.result

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.qrscannerpro.domain.model.ScanType
import com.example.qrscannerpro.domain.usecase.ClassifyContentUseCase
import com.example.qrscannerpro.domain.usecase.DetectUnsafeUrlUseCase

@Composable
fun ResultScreen(content: String, onBack: () -> Unit) {
    val context = LocalContext.current
    val type = ClassifyContentUseCase().invoke(content)
    val isUnsafeUrl = type == ScanType.URL && DetectUnsafeUrlUseCase().invoke(content)

    Scaffold(topBar = { TopAppBar(title = { Text("Scan Result") }) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Type: $type")
            Text(content)
            if (isUnsafeUrl) {
                Text("Potentially unsafe URL. Confirm before opening.")
            }
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                val intent = when (type) {
                    ScanType.URL -> Intent(Intent.ACTION_VIEW, Uri.parse(content))
                    ScanType.EMAIL -> Intent(Intent.ACTION_SENDTO, Uri.parse(content))
                    ScanType.PHONE -> Intent(Intent.ACTION_DIAL, Uri.parse(content))
                    ScanType.LOCATION -> Intent(Intent.ACTION_VIEW, Uri.parse(content))
                    else -> Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, content)
                    }
                }
                context.startActivity(intent)
            }) {
                Text("Smart Action")
            }
            TextButton(onClick = onBack) { Text("Back") }
        }
    }
}
