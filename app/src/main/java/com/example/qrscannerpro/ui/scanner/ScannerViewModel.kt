package com.example.qrscannerpro.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrscannerpro.data.repository.ScanRepository
import com.example.qrscannerpro.domain.model.ScanRecord
import com.example.qrscannerpro.domain.usecase.ClassifyContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val repository: ScanRepository,
    private val classify: ClassifyContentUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ScannerUiState())
    val state: StateFlow<ScannerUiState> = _state.asStateFlow()

    fun onCodeDetected(content: String) {
        if (_state.value.lastDetected == content) return
        val type = classify(content)
        _state.update { it.copy(lastDetected = content, latestType = type) }

        viewModelScope.launch {
            repository.save(ScanRecord(rawValue = content, type = type))
        }
    }

    fun resetLastDetected() {
        _state.update { it.copy(lastDetected = null) }
    }
}
