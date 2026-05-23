package com.taka.runejournal.feature.journal.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.taka.runejournal.core.domain.model.DrawnRune

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
  val timelineItemId: Int,
  @Embedded(prefix = "rune_") val rune: DrawnRune
)