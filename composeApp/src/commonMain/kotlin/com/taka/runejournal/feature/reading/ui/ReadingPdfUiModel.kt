package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.RuneSpread

data class ReadingPdfUiModel(
  val id: Long = 0L,
  val createdAt: String = "",
  val spread: RuneSpread = RuneSpread.SINGLE_RUNE,
  val topic: ReadingTopic = ReadingTopic.GENERAL,
  val recipient: String = "",
  val question: String? = null,
  val personalMessage: String? = null,
  val tabs: List<ReadingInterpretationTab.Rune> = emptyList()
  // TODO: Should the qr code image also be here?
)
