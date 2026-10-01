package com.taka.runejournal.feature.more.ui

import com.taka.runejournal.core.domain.model.ThemeMode

data class SettingsUiState(
  val reversedRunesEnabled: Boolean = true,
  val displayName: String = "",
  val themeMode: ThemeMode = ThemeMode.SYSTEM
)