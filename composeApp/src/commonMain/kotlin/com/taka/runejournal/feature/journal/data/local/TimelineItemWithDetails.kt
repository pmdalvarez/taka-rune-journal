package com.taka.runejournal.feature.journal.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.taka.runejournal.feature.journal.domain.model.TimelineItem
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
    rune = singleRuneReading.rune.toDomain()
  )

  ppfRuneReading != null -> TimelineItem.PpfRuneReading(
    id = timelineItem.id,
    createdAt = Instant.fromEpochMilliseconds(timelineItem.createdAt),
    notes = timelineItem.notes,
    pastRune = ppfRuneReading.pastRune.toDomain(),
    presentRune = ppfRuneReading.presentRune.toDomain(),
    futureRune = ppfRuneReading.futureRune.toDomain()

  )
  else -> TimelineItem.JournalEntry(
    id = timelineItem.id,
    createdAt = Instant.fromEpochMilliseconds(timelineItem.createdAt),
    notes = timelineItem.notes,
    imageFileName = timelineItem.imageFileName
  )
}

