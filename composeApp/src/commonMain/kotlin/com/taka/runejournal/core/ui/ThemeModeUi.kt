package com.taka.runejournal.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.taka.runejournal.core.domain.model.ThemeMode
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.theme_mode_dark
import taka_rune_journal.composeapp.generated.resources.theme_mode_light
import taka_rune_journal.composeapp.generated.resources.theme_mode_system

@Composable
fun ThemeMode.useDarkMode(): Boolean = when (this) {
  ThemeMode.LIGHT -> false
  ThemeMode.DARK -> true
  ThemeMode.SYSTEM -> isSystemInDarkTheme()
}

fun ThemeMode.label(): StringResource = when (this) {
  ThemeMode.LIGHT -> Res.string.theme_mode_light
  ThemeMode.DARK -> Res.string.theme_mode_dark
  ThemeMode.SYSTEM -> Res.string.theme_mode_system
}