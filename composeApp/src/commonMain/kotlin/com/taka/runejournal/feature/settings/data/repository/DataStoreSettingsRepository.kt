package com.taka.runejournal.feature.settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.taka.runejournal.feature.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreSettingsRepository(private val dataStore: DataStore<Preferences>) : SettingsRepository {

  override val username: Flow<String> = dataStore.data.map { it[USERNAME_KEY] ?: "" }

  override suspend fun areReversedRunesEnabled(): Boolean= dataStore.data.map { it[USE_REVERSED_RUNES_KEY] ?: true }.first()

  override suspend fun setReversedRunesEnabled(enabled: Boolean) {
    dataStore.edit { preferences ->
      preferences[USE_REVERSED_RUNES_KEY] = enabled
    }
  }

  override suspend fun setUsername(username: String) {
    dataStore.edit { preferences ->
      preferences[USERNAME_KEY] = username
    }
  }

  companion object {
    private val USERNAME_KEY = stringPreferencesKey("name")
    private val USE_REVERSED_RUNES_KEY = booleanPreferencesKey("USE_REVERSED_RUNES")
  }
}