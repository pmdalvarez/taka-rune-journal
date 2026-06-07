package com.taka.runejournal.feature.timeline.ui

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_entry_type_journal
import taka_rune_journal.composeapp.generated.resources.timeline_entry_type_ppf
import taka_rune_journal.composeapp.generated.resources.timeline_entry_type_single_rune
import kotlin.collections.List
import kotlin.time.Instant

private const val NOTE_PREVIEW_MAX_LENGTH = 140

data class TimelineItemUiModel(
  val id: Long,
  val createdAt: Instant,
  val entryTypeText: StringResource,
  val title: String?,
  val drawnRunes: List<DrawnRune>? = null,
  val notesPreview: String? = null,
)

fun TimelineItem.toUiModel(): TimelineItemUiModel = when (this) {
  is TimelineItem.SingleRuneReading -> TimelineItemUiModel(
    id = id,
    createdAt = createdAt,
    entryTypeText = Res.string.timeline_entry_type_single_rune,
    title = question,
    drawnRunes = listOf(rune)
  )

  is TimelineItem.PpfRuneReading -> TimelineItemUiModel(
    id = id,
    createdAt = createdAt,
    entryTypeText = Res.string.timeline_entry_type_ppf,
    title = question,
    drawnRunes = listOf(pastRune, presentRune, futureRune)
  )

  is TimelineItem.JournalEntry -> TimelineItemUiModel(
    id = id,
    createdAt = createdAt,
    title = title,
    entryTypeText = Res.string.timeline_entry_type_journal,
    notesPreview = notes.orEmpty().take(NOTE_PREVIEW_MAX_LENGTH)
  )
}
