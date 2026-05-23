package com.taka.runejournal.feature.journal.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TimelineItemDao {

  @Transaction
  @Query("SELECT * FROM timeline_items ORDER BY createdAt DESC")
  fun getTimelineItems(): PagingSource<Int, TimelineItemWithDetails>

}