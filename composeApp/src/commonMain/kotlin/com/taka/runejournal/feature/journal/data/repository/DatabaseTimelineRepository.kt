package com.taka.runejournal.feature.journal.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.taka.runejournal.core.domain.model.DrawnRune
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

  override suspend fun addJournalEntry(notes: String, imageFileName: String?) {

  }


  override suspend fun addSingleRuneReading(rune: DrawnRune, notes: String?) {

  }

  override suspend fun addPpfRuneReading(pastRune: DrawnRune, presentRune: DrawnRune, futureRune: DrawnRune, notes: String?) {

  }

  override suspend fun editTimelineItem(id: Int, notes: String?, imageFileName: String?) {

  }

  override suspend fun deleteTimelineitem(id: Int) {

  }


}