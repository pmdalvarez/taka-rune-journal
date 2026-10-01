package com.taka.runejournal.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.taka.runejournal.core.domain.model.ThemeMode
import com.taka.runejournal.core.ui.useDarkMode

private val LocalTakaDarkTheme = staticCompositionLocalOf { false }

private val takaLightColorScheme = lightColorScheme(
  // Main actions / filled buttons
  primary = TakaSlate,
  onPrimary = White,
  primaryContainer = TakaSlateSoft,
  onPrimaryContainer = TakaSlateDark,

  // Supporting actions / secondary emphasis
  secondary = TakaSlate,
  onSecondary = White,
  secondaryContainer = TakaSlateSoftVariant,
  onSecondaryContainer = TakaSlateDark,

  // Quiet/local actions
  tertiary = TakaSlate,
  onTertiary = White,
  tertiaryContainer = TakaSlateSoftVariant,
  onTertiaryContainer = TakaSlateDark,

  // Main app background
  background = Mist,
  onBackground = Ink,

  // Default surfaces: text fields, top-level content surfaces
  surface = White,
  onSurface = Ink,

  // Secondary surfaces
  surfaceVariant = Cloud,
  onSurfaceVariant = Flint,

  // Material 3 surface hierarchy
  surfaceContainerLowest = White,
  surfaceContainerLow = Snow,
  surfaceContainer = Cloud,
  surfaceContainerHigh = Ash,
  surfaceContainerHighest = Silver,
  surfaceDim = Ash,
  surfaceBright = White,

  // Borders / outlines
  outline = Stone,
  outlineVariant = Silver,

  // Inverse surfaces
  inverseSurface = Ink,
  inverseOnSurface = White,
  inversePrimary = TakaSlateSoft,

  // Error
  error = Crimson,
  onError = White,
  errorContainer = Blush,
  onErrorContainer = DeepCrimson,

  scrim = Black,
)

private val takaDarkColorScheme = darkColorScheme(
  // Main actions / filled buttons
  primary = TakaSlateDarkPrimary,
  onPrimary = Ink,
  primaryContainer = TakaSlateDarkContainer,
  onPrimaryContainer = TakaSlateSoft,

  // Supporting actions / secondary emphasis
  secondary = TakaSlateDarkPrimary,
  onSecondary = Ink,
  secondaryContainer = TakaSlateDarkContainerVariant,
  onSecondaryContainer = TakaSlateSoft,

  // Quiet/local actions
  tertiary = TakaSlateDarkPrimary,
  onTertiary = Ink,
  tertiaryContainer = TakaSlateDarkContainerVariant,
  onTertiaryContainer = TakaSlateSoft,

  // Main app background
  background = Ink,
  onBackground = Porcelain,

  // Default surfaces
  surface = Coal,
  onSurface = Porcelain,

  // Secondary surfaces
  surfaceVariant = Charcoal,
  onSurfaceVariant = Stone,

  // Material 3 surface hierarchy
  surfaceContainerLowest = Ink,
  surfaceContainerLow = Ebony,
  surfaceContainer = Coal,
  surfaceContainerHigh = Iron,
  surfaceContainerHighest = Charcoal,
  surfaceDim = Ink,
  surfaceBright = Graphite,

  // Borders / outlines
  outline = Slate,
  outlineVariant = Graphite,

  // Inverse surfaces
  inverseSurface = Porcelain,
  inverseOnSurface = Ink,
  inversePrimary = TakaSlate,

  // Error
  error = TakaErrorDark,
  onError = TakaOnErrorDark,
  errorContainer = TakaErrorContainerDark,
  onErrorContainer = TakaOnErrorContainerDark,

  scrim = Black,
)


@Composable
fun TakaTheme(
  themeMode: ThemeMode = ThemeMode.SYSTEM,
  content: @Composable () -> Unit,
) {
  val useDarkMode = themeMode.useDarkMode()

  CompositionLocalProvider(
    LocalTakaDarkTheme provides useDarkMode,
  ) {
    MaterialTheme(
      colorScheme = if (useDarkMode) {
        takaDarkColorScheme
      } else {
        takaLightColorScheme
      },
      typography = takaTypography,
      shapes = takaShapes,
      content = content,
    )
  }
}

@Composable
fun isAppInDarkTheme(): Boolean = LocalTakaDarkTheme.current