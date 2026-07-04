package com.taka.runejournal.feature.reading.ui

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

data class NewReadingUiState (
  val question: String = "",
  val topic: ReadingTopic = ReadingTopic.GENERAL,
  val spread: RuneSpread = RuneSpread.SINGLE_RUNE,
  val result: SpreadResult? = null
)