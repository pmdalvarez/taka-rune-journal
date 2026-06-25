package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingCategory
import com.taka.runejournal.core.domain.model.ReadingPosition
import kotlin.time.Instant

data class RuneInterpretation(
  val position: ReadingPosition,
  val rune: DrawnRune,
  val interpretation: String
)

data class ReadingInterpretationUiState(
  val id: Long = 0L,
  val createdAt: Instant = Instant.DISTANT_PAST,
  val notes: String? = null,
  val category: ReadingCategory = ReadingCategory.GENERAL,
  val question: String? = null,
  val runeInterpretations: List<RuneInterpretation> = emptyList(),
  val summary: String? = null, // AI-generated summary of whole reading, nice to have
  val showDeleteDialog: Boolean = false
)