package com.taka.runejournal.feature.settings.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

  val username: Flow<String>

  suspend fun areReversedRunesEnabled(): Boolean

  suspend fun setReversedRunesEnabled(enabled: Boolean)

  suspend fun setUsername(username: String)
}