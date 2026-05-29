package com.taka.runejournal.feature.timeline.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import kotlin.time.Clock

@Entity(tableName = "timeline_items")
data class TimelineItemEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
  val notes: String?,
  val imageFileName: String? = null,
)

fun TimelineItem.toTimelineItemEntity(): TimelineItemEntity = TimelineItemEntity(
  id = id,
  createdAt = createdAt.toEpochMilliseconds(),
  notes = notes
)

fun TimelineItem.JournalEntry.toTimelineItemEntity(): TimelineItemEntity = TimelineItemEntity(
  id = id,
  createdAt = createdAt.toEpochMilliseconds(),
  notes = notes,
  imageFileName = imageFileName
)

