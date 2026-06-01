package com.taka.runejournal.feature.timeline.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.taka.runejournal.core.domain.model.ReadingCategory
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import kotlin.time.Instant

data class TimelineItemWithDetails(
  @Embedded
  val timelineItem: TimelineItemEntity,
  @Relation(
    parentColumn = "id",
    entityColumn = "timelineItemId"
  )
  val singleRuneReading: SingleRuneReadingEntity?,
  @Relation(
    parentColumn = "id",
    entityColumn = "timelineItemId"
  )
  val ppfRuneReading: PpfRuneReadingEntity?
)

fun TimelineItemWithDetails.toTimelineItem() = when {
  singleRuneReading != null -> TimelineItem.SingleRuneReading(
    id = timelineItem.id,
    createdAt = Instant.fromEpochMilliseconds(timelineItem.createdAt),
    notes = timelineItem.notes,
    question = singleRuneReading.question,
    category = ReadingCategory.fromKey(singleRuneReading.category) ?: error("Unknown category: $ppfRuneReading.category"),
    rune = singleRuneReading.rune.toDomain()
  )
  ppfRuneReading != null -> TimelineItem.PpfRuneReading(
    id = timelineItem.id,
    createdAt = Instant.fromEpochMilliseconds(timelineItem.createdAt),
    notes = timelineItem.notes,
    question = ppfRuneReading.question,
    category = ReadingCategory.fromKey(ppfRuneReading.category) ?: error("Unknown category: $ppfRuneReading.category"),
    pastRune = ppfRuneReading.pastRune.toDomain(),
    presentRune = ppfRuneReading.presentRune.toDomain(),
    futureRune = ppfRuneReading.futureRune.toDomain()

  )
  else -> TimelineItem.JournalEntry(
    id = timelineItem.id,
    createdAt = Instant.fromEpochMilliseconds(timelineItem.createdAt),
    notes = timelineItem.notes,
    title = timelineItem.title
  )
}

