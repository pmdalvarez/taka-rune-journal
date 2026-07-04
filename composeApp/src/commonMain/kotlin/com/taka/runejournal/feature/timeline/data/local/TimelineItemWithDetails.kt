package com.taka.runejournal.feature.timeline.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.taka.runejournal.core.domain.model.ReadingTopic
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
    topic = ReadingTopic.fromKey(singleRuneReading.category) ?: error("Unknown topic: $ppfRuneReading.category"),
    drawnRune = singleRuneReading.rune.toDomain()
  )
  ppfRuneReading != null -> TimelineItem.PpfRuneReading(
    id = timelineItem.id,
    createdAt = Instant.fromEpochMilliseconds(timelineItem.createdAt),
    notes = timelineItem.notes,
    question = ppfRuneReading.question,
    topic = ReadingTopic.fromKey(ppfRuneReading.category) ?: error("Unknown topic: $ppfRuneReading.category"),
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

