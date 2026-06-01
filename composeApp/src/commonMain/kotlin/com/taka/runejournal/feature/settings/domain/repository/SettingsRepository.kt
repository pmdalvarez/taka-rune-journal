package com.taka.runejournal.feature.settings.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

  val reversedRunesEnabled: Flow<Boolean>

  val displayName: Flow<String>

  suspend fun setReversedRunesEnabled(enabled: Boolean)

  suspend fun setDisplayName(username: String)
}