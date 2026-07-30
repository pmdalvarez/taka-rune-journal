package com.taka.runejournal.feature.reading.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap

data class RuneImageDrawState(
  val image: ImageBitmap,
  val center: Offset,
  val angle: Float,
  val alpha: Float = 1f
)