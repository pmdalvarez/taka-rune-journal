package com.taka.runejournal.feature.journal.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.taka.runejournal.core.domain.model.DrawnRune

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
  val timelineItemId: Int,
  @Embedded(prefix = "past_rune_") val pastRune: DrawnRune,
  @Embedded(prefix = "present_rune_") val presentRune: DrawnRune,
  @Embedded(prefix = "future_rune_") val futureRune: DrawnRune
)