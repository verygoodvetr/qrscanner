package com.example.qrscannerpro.ui.scanner

import android.Manifest
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.qrscannerpro.core.QrAnalyzer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScannerScreen(
    onOpenResult: (String) -> Unit,
    onOpenHistory: () -> Unit,
    onOpenGenerator: () -> Unit,
    onOpenSettings: () -> Unit,
    vm: ScannerViewModel = hiltViewModel(),
) {
    val state by vm.state.collectAsState()
    val permission = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(state.lastDetected) {
        state.lastDetected?.let(onOpenResult)
    }

    if (!permission.status.isGranted) {
        Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
            Text("Camera permission required for scanning.")
            Button(onClick = { permission.launchPermissionRequest() }) { Text("Grant") }
        }
        return
    }

    Box(Modifier.fillMaxSize()) {
        CameraPreview(onDetected = vm::onCodeDetected)
        ScannerOverlay()
        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            FloatingActionButton(onClick = onOpenHistory) { Icon(Icons.Default.History, null) }
            FloatingActionButton(onClick = onOpenGenerator) { Icon(Icons.Default.QrCode, null) }
            FloatingActionButton(onClick = onOpenSettings) { Icon(Icons.Default.Settings, null) }
        }
    }
}

@Composable
private fun CameraPreview(onDetected: (String) -> Unit) {
    val context = LocalContext.current
    val cameraExecutor = Executors.newSingleThreadExecutor()

    AndroidView(
        factory = {
            val previewView = PreviewView(context)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = androidx.camera.core.Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }
                val analysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, QrAnalyzer(onDetected))
                    }

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    context as androidx.lifecycle.LifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    analysis,
                )
            }, ContextCompat.getMainExecutor(context))
            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun ScannerOverlay() {
    val transition = rememberInfiniteTransition(label = "scan")
    val alpha by transition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(900), repeatMode = RepeatMode.Reverse),
        label = "frameAlpha"
    )
    Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.25f))) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.72f)
                .height(260.dp)
                .alpha(alpha)
                .border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
        )
    }
}
