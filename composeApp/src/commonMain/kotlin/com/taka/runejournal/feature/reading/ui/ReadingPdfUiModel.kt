package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.ReadingTopic

data class ReadingPdfUiModel(
  val id: Long = 0L,
  val createdAt: String = "",
  val topic: ReadingTopic = ReadingTopic.GENERAL,
  val recipient: String = "",
  val question: String? = null,
  val personalMessage: String? = null,
  val tabs: List<ReadingInterpretationTab.Rune> = emptyList()
)
