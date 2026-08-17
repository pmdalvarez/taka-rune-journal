package com.taka.runejournal.feature.reading.ui

import androidx.compose.ui.geometry.Size
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.RuneSpread

data class NewReadingUiState (
  val spread: RuneSpread? = null,
  val question: String? = null,
  val topic: ReadingTopic? = null,
  val canvasSize: Size = Size.Zero
)