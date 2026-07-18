package com.taka.runejournal.feature.reading.domain.model

import androidx.compose.ui.geometry.Offset

data class RuneVisualState(
    val center: Offset, // Center point of the rune within the canvas, in pixels.
    val depth: Float, // Visual stacking order. Higher values are drawn above lower values.
    val angle: Float //  Rotation angle in degrees.
)