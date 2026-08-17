package com.taka.runejournal.feature.timeline.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TimelineItemDao {

  @Transaction
  @Query("SELECT * FROM timeline_items ORDER BY createdAt DESC")
  fun getTimelineItems(): PagingSource<Int, TimelineItemWithDetails>

  @Transaction
  @Query("SELECT * FROM timeline_items WHERE id = :id")
  suspend fun getTimelineItem(id: Long): TimelineItemWithDetails?

  @Insert
  suspend fun insert(timelineItemEntity: TimelineItemEntity): Long

  @Insert
  suspend fun insert(singleRuneReadingEntity: SingleRuneReadingEntity): Long

  @Insert
  suspend fun insert(ppfRuneReadingEntity: PpfRuneReadingEntity): Long

  @Transaction
  suspend fun insertSingleRuneReading(
    timelineItemEntity: TimelineItemEntity,
    question: String?,
    category: String,
    rune: DrawnRuneEmbedded,
  ): Long {
    val timelineItemId = insert(timelineItemEntity)
    val singleRuneReadingEntity = SingleRuneReadingEntity(
      timelineItemId = timelineItemId,
      question = question,
      category = category,
      rune = rune,
    )
    return insert(singleRuneReadingEntity)
  }

  @Transaction
  suspend fun insertPpfRuneReading(
    timelineItemEntity: TimelineItemEntity,
    question: String?,
    category: String,
    pastRune: DrawnRuneEmbedded,
    presentRune: DrawnRuneEmbedded,
    futureRune: DrawnRuneEmbedded
  ): Long {
    val timelineItemId = insert(timelineItemEntity)
    val ppfRuneReadingEntity = PpfRuneReadingEntity(
      timelineItemId = timelineItemId,
      question = question,
      category = category,
      pastRune = pastRune,
      presentRune = presentRune,
      futureRune = futureRune
    )
    return insert(ppfRuneReadingEntity)
  }

  @Query("UPDATE timeline_items SET notes = :notes, title = :title WHERE id = :id")
  suspend fun updateTimelineItem(id: Long, notes: String?, title: String?): Int

  @Query("DELETE FROM timeline_items WHERE id = :id")
  suspend fun deleteTimelineItem(id: Long): Int

}