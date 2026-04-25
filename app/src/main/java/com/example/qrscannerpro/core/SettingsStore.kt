package com.example.qrscannerpro.core

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class SettingsStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val vibrationKey = booleanPreferencesKey("vibration_enabled")
    private val soundKey = booleanPreferencesKey("sound_enabled")

    val vibrationEnabled: Flow<Boolean> = context.dataStore.data.map { it[vibrationKey] ?: true }
    val soundEnabled: Flow<Boolean> = context.dataStore.data.map { it[soundKey] ?: true }

    suspend fun setVibration(enabled: Boolean) {
        context.dataStore.edit { it[vibrationKey] = enabled }
    }

    suspend fun setSound(enabled: Boolean) {
        context.dataStore.edit { it[soundKey] = enabled }
    }
}
