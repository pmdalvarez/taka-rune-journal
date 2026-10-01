package com.taka.runejournal.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.taka.runejournal.core.domain.model.ThemeMode
import com.taka.runejournal.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreSettingsRepository(private val dataStore: DataStore<Preferences>) : SettingsRepository {

  override val reversedRunesEnabled: Flow<Boolean> = dataStore.data.map { it[REVERSED_RUNES_ENABLED_KEY] ?: true }

  override val displayName: Flow<String> = dataStore.data.map { it[DISPLAY_NAME_KEY] ?: "" }

  override val themeMode: Flow<ThemeMode> = dataStore
    .data
    .map {
      val key = it[THEME_MODE_KEY] ?: ""
      ThemeMode.entries.find { it.name == key } ?: ThemeMode.SYSTEM
    }

  override suspend fun setReversedRunesEnabled(enabled: Boolean) {
    dataStore.edit { preferences ->
      preferences[REVERSED_RUNES_ENABLED_KEY] = enabled
    }
  }

  override suspend fun setDisplayName(displayName: String) {
    dataStore.edit { preferences ->
      preferences[DISPLAY_NAME_KEY] = displayName
    }
  }

  override suspend fun setThemeMode(themeMode: ThemeMode) {
    dataStore.edit { preferences ->
      preferences[THEME_MODE_KEY] = themeMode.name
    }
  }

  companion object {
    private val DISPLAY_NAME_KEY = stringPreferencesKey("display_name")
    private val REVERSED_RUNES_ENABLED_KEY = booleanPreferencesKey("reversed_runes_enabled")
    private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
  }
}