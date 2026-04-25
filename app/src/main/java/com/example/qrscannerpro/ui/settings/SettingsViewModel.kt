package com.example.qrscannerpro.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrscannerpro.core.SettingsStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsStore: SettingsStore,
) : ViewModel() {
    val uiState: StateFlow<SettingsUiState> = combine(
        settingsStore.vibrationEnabled,
        settingsStore.soundEnabled,
    ) { vibration, sound ->
        SettingsUiState(vibration, sound)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsUiState())

    fun onVibrationChanged(enabled: Boolean) = viewModelScope.launch { settingsStore.setVibration(enabled) }
    fun onSoundChanged(enabled: Boolean) = viewModelScope.launch { settingsStore.setSound(enabled) }
}
