package com.taka.runejournal.feature.reading.ui

import androidx.compose.ui.geometry.Offset
import com.taka.runejournal.core.domain.model.Rune

data class DraggedRuneState(
  val rune: Rune,
  val anchorFromCenter: Offset,
  val initialFingerAngle: Float,
  val initialRuneAngle: Float,
)

