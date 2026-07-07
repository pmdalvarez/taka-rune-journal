package com.taka.runejournal.feature.reading.domain.model

import androidx.compose.ui.geometry.Offset

data class RuneVisualState(
    val position: Offset,
    val depth: Float,
    val angle: Float
)