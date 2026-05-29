package com.taka.runejournal.feature.timeline.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface TimelineItemDao {

  @Transaction
  @Query("SELECT * FROM timeline_items ORDER BY createdAt DESC")
  fun getTimelineItems(): PagingSource<Int, TimelineItemWithDetails>

  @Upsert
  suspend fun insert(timelineItemEntity: TimelineItemEntity) : Long

  @Upsert
  suspend fun insert(singleRuneReadingEntity: SingleRuneReadingEntity) : Long

  @Upsert
  suspend fun insert(ppfRuneReadingEntity: PpfRuneReadingEntity) : Long

  @Transaction
  suspend fun insertSingleRuneReading(
    timelineItemEntity: TimelineItemEntity,
    rune: DrawnRuneEmbedded,
  ) {
    val timelineItemId = insert(timelineItemEntity)
    val singleRuneReadingEntity = SingleRuneReadingEntity(
      timelineItemId = timelineItemId,
      rune = rune,
    )
    insert(singleRuneReadingEntity)
  }

  @Transaction
  suspend fun insertPpfRuneReading(
    timelineItemEntity: TimelineItemEntity,
    pastRune: DrawnRuneEmbedded,
    presentRune: DrawnRuneEmbedded,
    futureRune: DrawnRuneEmbedded
  ) {
    val timelineItemId = insert(timelineItemEntity)
    val ppfRuneReadingEntity = PpfRuneReadingEntity(
      timelineItemId = timelineItemId,
      pastRune = pastRune,
      presentRune = presentRune,
      futureRune = futureRune
    )
    insert(ppfRuneReadingEntity)
  }

  @Query("UPDATE timeline_items SET notes = :notes, imageFileName = :imageFileName WHERE id = :id")
  suspend fun updateTimelineItem(id: Long, notes: String?, imageFileName: String?): Int

  @Query("DELETE FROM timeline_items WHERE id = :id")
  suspend fun deleteTimelineItem(id: Long): Int

}