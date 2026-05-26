package com.taka.runejournal.feature.journal.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.taka.runejournal.feature.journal.data.local.TimelineItemDao
import com.taka.runejournal.feature.journal.data.local.toTimelineItem
import com.taka.runejournal.feature.journal.domain.model.TimelineItem
import com.taka.runejournal.feature.journal.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseTimelineRepository(private val timelineItemDao: TimelineItemDao) : TimelineRepository {

  override fun observePagedTimelineItems(): Flow<PagingData<TimelineItem>> {
    return Pager(
      config = PagingConfig(pageSize = 30),
      pagingSourceFactory = { timelineItemDao.getTimelineItems() }
    ).flow.map { pagingData ->
      pagingData.map { it.toTimelineItem() }
    }
  }

  override suspend fun addJournalEntry(journalEntry: TimelineItem.JournalEntry) {

  }

  override suspend fun addSingleRuneReading(reading: TimelineItem.SingleRuneReading) {

  }

  override suspend fun addPpfRuneReading(reading: TimelineItem.PpfRuneReading) {

  }

  override suspend fun editTimelineItem(id: Int, notes: String) {

  }

  override suspend fun deleteTimelineitem(id: Int) {

  }


}