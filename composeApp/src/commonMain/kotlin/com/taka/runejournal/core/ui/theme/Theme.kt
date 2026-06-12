package com.taka.runejournal.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val takaDarkColorScheme = darkColorScheme(
  primary = RuneGold,
  onPrimary = NearBlack,
  primaryContainer = DarkClay,
  onPrimaryContainer = PureWhite,

  secondary = Lichen,
  onSecondary = NearBlack,
  secondaryContainer = DeepMoss,
  onSecondaryContainer = PureWhite,

  tertiary = Ochre,
  onTertiary = NearBlack,
  tertiaryContainer = WalnutEarth,
  onTertiaryContainer = PureWhite,

  background = RuneStoneDark,
  onBackground = PureWhite,

  surface = Basalt,
  onSurface = PureWhite,

  surfaceVariant = WornStone,
  onSurfaceVariant = LightGray,

  surfaceContainerLowest = DeepBasalt,
  surfaceContainerLow = Basalt,
  surfaceContainer = RaisedStone,
  surfaceContainerHigh = WornStone,
  surfaceContainerHighest = RuneStone,

  surfaceDim = DeepBasalt,
  surfaceBright = WornStone,

  outline = StoneEdge,
  outlineVariant = RuneStone,

  inverseSurface = SoftWhite,
  inverseOnSurface = NearBlack,
  inversePrimary = BurntClay,

  error = EmberRed,
  onError = NearBlack,
  errorContainer = DarkEmber,
  onErrorContainer = PureWhite,

  scrim = NearBlack,
)

@Composable
fun TakaTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = takaDarkColorScheme,
    typography = takaTypography,
    shapes = takaShapes,
    content = content,
  )
}