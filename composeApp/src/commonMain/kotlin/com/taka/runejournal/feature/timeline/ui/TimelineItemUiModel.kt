package com.taka.runejournal.feature.timeline.ui

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.ui.utils.format
import com.taka.runejournal.feature.reading.ui.readingType
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.ic_journal_entry_icon
import taka_rune_journal.composeapp.generated.resources.ic_rune_reading_icon
import taka_rune_journal.composeapp.generated.resources.timeline_item_type_journal_entry
import kotlin.collections.List

private const val NOTE_PREVIEW_MAX_LENGTH = 140

data class TimelineItemUiModel(
  val id: Long,
  val isJournalEntry: Boolean,
  val formattedDate: String,
  val icon: DrawableResource,
  val typeRes: StringResource,
  val title: String? = null,
  val drawnRunes: List<DrawnRune>? = null,
  val preview: String? = null,
)

fun TimelineItem.toUiModel(): TimelineItemUiModel = when (this) {
  is TimelineItem.SingleRuneReading -> TimelineItemUiModel(
    id = id,
    isJournalEntry = false,
    formattedDate = createdAt.format(),
    icon = Res.drawable.ic_rune_reading_icon,
    typeRes = category.readingType(),
    drawnRunes = listOf(rune),
    preview = question
  )

  is TimelineItem.PpfRuneReading -> TimelineItemUiModel(
    id = id,
    isJournalEntry = false,
    formattedDate = createdAt.format(),
    icon = Res.drawable.ic_rune_reading_icon,
    typeRes = category.readingType(),
    drawnRunes = listOf(pastRune, presentRune, futureRune),
    preview = question
  )

  is TimelineItem.JournalEntry -> TimelineItemUiModel(
    id = id,
    isJournalEntry = true,
    formattedDate = createdAt.format(),
    icon = Res.drawable.ic_journal_entry_icon,
    title = title,
    typeRes = Res.string.timeline_item_type_journal_entry,
    preview = notes.orEmpty().take(NOTE_PREVIEW_MAX_LENGTH)
  )

}