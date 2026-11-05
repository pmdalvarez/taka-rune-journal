package com.taka.runejournal.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.taka.runejournal.core.domain.repository.AccessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreAccessRepository(private val dataStore: DataStore<Preferences>) : AccessRepository {

  override val hasFullAccess: Flow<Boolean> = dataStore.data.map { it[HAS_FULL_ACCESS_KEY] ?: false }

  override suspend fun setHasFullAccess(hasFullAccess: Boolean) {
    dataStore.edit { preferences ->
      preferences[HAS_FULL_ACCESS_KEY] = hasFullAccess
    }
  }

  companion object {
    private val HAS_FULL_ACCESS_KEY = booleanPreferencesKey("has_full_access")
  }
}
