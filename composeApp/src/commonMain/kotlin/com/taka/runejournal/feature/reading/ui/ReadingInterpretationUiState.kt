package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.feature.reading.domain.model.ReadingQrPayload
import org.jetbrains.compose.resources.StringResource

sealed class ReadingInterpretationTabUiState {
  abstract val label: StringResource

  data class Rune(
    override val label: StringResource,
    val drawnRune: DrawnRune,
    val interpretation: StringResource,
    val supplementalInterpretation: StringResource?,
    val keywords: StringResource,
    val supplementalKeywords: StringResource?,
    val tabDescription: StringResource?
  ) : ReadingInterpretationTabUiState()

  data class Notes(
    override val label: StringResource,
    val notes: String?
  ): ReadingInterpretationTabUiState()

  data class Share(
    override val label: StringResource,
    val recipient: String?,
    val notes: String?,
    val pdfUiModel: ReadingPdfUiModel,
  ): ReadingInterpretationTabUiState()
}

data class ReadingInterpretationUiState(
  val id: Long = 0L,
  val createdAt: String = "",
  val topic: ReadingTopic = ReadingTopic.GENERAL,
  val recipient: String? = null,
  val question: String? = null,
  val tabs: List<ReadingInterpretationTabUiState> = emptyList(),
  val showDeleteDialog: Boolean = false
)