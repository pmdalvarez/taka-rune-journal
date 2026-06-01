package com.taka.runejournal.feature.timeline.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
  tableName = "single_rune_readings",
  foreignKeys = [ForeignKey(
    entity = TimelineItemEntity::class,
    parentColumns = ["id"],
    childColumns = ["timelineItemId"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
  )]
)
data class SingleRuneReadingEntity(
  @PrimaryKey
  val timelineItemId: Long,
  val question: String?,
  val category: String,
  @Embedded(prefix = "rune_") val rune: DrawnRuneEmbedded
)
