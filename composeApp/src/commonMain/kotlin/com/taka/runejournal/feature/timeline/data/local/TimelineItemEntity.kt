package com.taka.runejournal.feature.timeline.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock

@Entity(tableName = "timeline_items")
data class TimelineItemEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
  val notes: String? = null,
  val title: String? = null,
)