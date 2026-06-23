package com.taka.runejournal.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val takaLightColorScheme = lightColorScheme(
  // Main actions / filled buttons
  primary = Charcoal,
  onPrimary = White,
  primaryContainer = Ash,
  onPrimaryContainer = Ink,

  // Secondary actions
  secondary = Graphite,
  onSecondary = White,
  secondaryContainer = Cloud,
  onSecondaryContainer = Ink,

  // Tertiary actions
  tertiary = Slate,
  onTertiary = White,
  tertiaryContainer = Ash,
  onTertiaryContainer = Ink,

  // Main app background
  background = Mist,
  onBackground = Ink,

  // Default surfaces: text fields, top-level content surfaces
  surface = White,
  onSurface = Ink,

  // Secondary surfaces
  surfaceVariant = Cloud,
  onSurfaceVariant = Graphite,

  // Material 3 surface hierarchy
  // Use these for cards, timeline rows, menus, containers, etc.
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
  inversePrimary = White,

  // Error
  error = Crimson,
  onError = White,
  errorContainer = Blush,
  onErrorContainer = DeepCrimson,

  scrim = Black,
)

@Composable
fun TakaTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = takaLightColorScheme,
    typography = takaTypography,
    shapes = takaShapes,
    content = content,
  )
}