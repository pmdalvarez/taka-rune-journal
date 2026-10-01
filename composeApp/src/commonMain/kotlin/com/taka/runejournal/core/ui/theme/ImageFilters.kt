package com.taka.runejournal.core.ui.theme

import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix

val DarkThemeRuneColorFilter = ColorFilter.colorMatrix(
  ColorMatrix().apply {
    setToScale(
      redScale = 2f,
      greenScale = 2f,
      blueScale = 2f,
      alphaScale = 1f,
    )
  }
)