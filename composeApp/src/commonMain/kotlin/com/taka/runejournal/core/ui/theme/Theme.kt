package com.taka.runejournal.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val takaLightColorScheme = lightColorScheme(
  primary = Walnut,
  onPrimary = Bone,
  primaryContainer = Clay,
  onPrimaryContainer = Ink,

  secondary = Ash,
  onSecondary = Bone,
  secondaryContainer = Parchment,
  onSecondaryContainer = Ink,

  tertiary = Moss,
  onTertiary = Bone,
  tertiaryContainer = PaleMoss,
  onTertiaryContainer = Ink,

  background = Parchment,
  onBackground = Ink,

  surface = Bone,
  onSurface = Ink,
  surfaceVariant = Sand,
  onSurfaceVariant = Ash,

  outline = Clay,
  outlineVariant = LightClay,

  error = ErrorRed,
  onError = Color.White,
  errorContainer = ErrorBlush,
  onErrorContainer = ErrorInk,
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