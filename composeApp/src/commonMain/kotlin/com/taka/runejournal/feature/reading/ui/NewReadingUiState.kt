package com.taka.runejournal.feature.reading.ui

import androidx.compose.ui.geometry.Size
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.RuneSpread

sealed interface SpreadResult {

  data class SingleRune(
    val drawnRune: DrawnRune,
  ) : SpreadResult

  data class PastPresentFuture(
    val pastRune: DrawnRune,
    val presentRune: DrawnRune,
    val futureRune: DrawnRune,
  ) : SpreadResult
}

enum class DrawPhase {
  CHOOSE, // user is choosing runes
  REVEAL, // runes being revealed
}

data class NewReadingUiState (
  val spread: RuneSpread? = null,
  val question: String? = null,
  val topic: ReadingTopic? = null,
  val canvasSize: Size = Size.Zero,
  val drawPhase: DrawPhase = DrawPhase.CHOOSE,
  val result: SpreadResult? = null
)