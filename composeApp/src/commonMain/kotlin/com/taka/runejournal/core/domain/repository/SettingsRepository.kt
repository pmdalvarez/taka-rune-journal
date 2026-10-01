package com.taka.runejournal.core.domain.repository

import com.taka.runejournal.core.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

  val reversedRunesEnabled: Flow<Boolean>

  val displayName: Flow<String>

  val themeMode: Flow<ThemeMode>

  suspend fun setReversedRunesEnabled(enabled: Boolean)

  suspend fun setDisplayName(username: String)

  suspend fun setThemeMode(themeMode: ThemeMode)
}