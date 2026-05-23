package com.taka.runejournal.feature.journal.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timeline_items")
data class TimelineItemEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val createdAt: Long,
  val notes: String,
  val imageFileName: String?,
)