package com.taka.runejournal.feature.more.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.taka.runejournal.feature.more.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreSettingsRepository(private val dataStore: DataStore<Preferences>) : SettingsRepository {

  override val reversedRunesEnabled: Flow<Boolean> = dataStore.data.map { it[REVERSED_RUNES_ENABLED_KEY] ?: true }

  override val displayName: Flow<String> = dataStore.data.map { it[DISPLAY_NAME_KEY] ?: "" }

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

  companion object {
    private val DISPLAY_NAME_KEY = stringPreferencesKey("display_name")
    private val REVERSED_RUNES_ENABLED_KEY = booleanPreferencesKey("reversed_runes_enabled")
  }
}