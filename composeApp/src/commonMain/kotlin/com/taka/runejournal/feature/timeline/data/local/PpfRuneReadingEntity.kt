package com.taka.runejournal.feature.timeline.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
  tableName = "ppf_rune_readings",
  foreignKeys = [ForeignKey(
    entity = TimelineItemEntity::class,
    parentColumns = ["id"],
    childColumns = ["timelineItemId"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
  )]
)
data class PpfRuneReadingEntity(
  @PrimaryKey
  val timelineItemId: Long,
  @Embedded(prefix = "past_rune_") val pastRune: DrawnRuneEmbedded,
  @Embedded(prefix = "present_rune_") val presentRune: DrawnRuneEmbedded,
  @Embedded(prefix = "future_rune_") val futureRune: DrawnRuneEmbedded
)
