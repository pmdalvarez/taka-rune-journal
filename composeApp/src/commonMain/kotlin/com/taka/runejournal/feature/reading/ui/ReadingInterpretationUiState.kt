package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingCategory
import org.jetbrains.compose.resources.StringResource

sealed class ReadingInterpretationTab {
  abstract val label: StringResource

  data class Rune(
    override val label: StringResource,
    val drawnRune: DrawnRune,
    val interpretation: StringResource,
    val supplementalInterpretation: StringResource?,
    val keywords: StringResource,
    val supplementalKeywords: StringResource?,
    val positionDescription: StringResource?
  ) : ReadingInterpretationTab()

// TODO Implement this in the future
//  data class Summary(
//    override val label: StringResource,
//    val summary: String,
//  ) : ReadingInterpretationTab()

  data class Notes(
    override val label: StringResource,
    val notes: String? = null,
  ): ReadingInterpretationTab()
}

data class ReadingInterpretationUiState(
  val id: Long = 0L,
  val createdAt: String = "",
  val category: ReadingCategory = ReadingCategory.GENERAL,
  val question: String? = null,
  val tabs: List<ReadingInterpretationTab> = emptyList(),
  val showDeleteDialog: Boolean = false
)