package com.taka.runejournal.feature.reading.ui

import androidx.compose.ui.geometry.Offset

data class RuneVisualState(
  val center: Offset, // Center point of the rune within the canvas, in pixels.
  val depth: Float, // Visual stacking order. Higher values are drawn above lower values.
  val angle: Float, //  Rotation angle in degrees.
  val alpha: Float = 1f, // For crossFading if rune is being selected or selected, represents alpha of unselected image
)