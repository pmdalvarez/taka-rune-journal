package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.core.domain.model.RuneOrientation
import com.taka.runejournal.core.domain.model.RuneSpread
import com.taka.runejournal.feature.reading.domain.model.ReadingQrPayload
import org.jetbrains.compose.resources.StringResource

data class ReadingPdfDrawnRuneUiModel(
  val rune: Rune,
  val orientation: RuneOrientation,
  val position: StringResource?,
  val positionDescription: StringResource?,
  val interpretation: StringResource,
  val supplementalInterpretation: StringResource?,
  val keywords: StringResource,
  val supplementalKeywords: StringResource?
)

data class ReadingPdfUiModel(
  val id: Long,
  val createdAt: String,
  val spread: RuneSpread,
  val topic: ReadingTopic,
  val recipient: String,
  val question: String?,
  val personalMessage: String?,
  val qrPayload: ReadingQrPayload,
  val runes: List<ReadingPdfDrawnRuneUiModel>,
)
