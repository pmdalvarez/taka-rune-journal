package com.taka.runejournal.feature.timeline.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.feature.timeline.data.local.TimelineItemDao
import com.taka.runejournal.feature.timeline.data.local.TimelineItemEntity
import com.taka.runejournal.feature.timeline.data.local.toEmbedded
import com.taka.runejournal.feature.timeline.data.local.toTimelineItem
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseTimelineRepository(private val timelineItemDao: TimelineItemDao) : TimelineRepository {

  override fun observeTimelineItems(): Flow<PagingData<TimelineItem>> {
    return Pager(
      config = PagingConfig(pageSize = 30),
      pagingSourceFactory = { timelineItemDao.getTimelineItems() }
    ).flow.map { pagingData ->
      pagingData.map { it.toTimelineItem() }
    }
  }

  override suspend fun getTimelineItem(id: Long): TimelineItem? = timelineItemDao.getTimelineItem(id)?.toTimelineItem()

  override suspend fun addJournalEntry(notes: String, imageFileName: String?) {
    val timelineItemEntity = TimelineItemEntity(
      notes = notes,
      imageFileName = imageFileName
    )
    timelineItemDao.insert(timelineItemEntity)
  }

  override suspend fun addSingleRuneReading(rune: DrawnRune, notes: String?) {
    val timelineItemEntity = TimelineItemEntity(notes = notes)
    val runeEmbedded = rune.toEmbedded()
    timelineItemDao.insertSingleRuneReading(timelineItemEntity, runeEmbedded)
  }

  override suspend fun addPpfRuneReading(pastRune: DrawnRune, presentRune: DrawnRune, futureRune: DrawnRune, notes: String?) {
    val timelineItemEntity = TimelineItemEntity(notes = notes)
    val pastRuneEmbedded = pastRune.toEmbedded()
    val presentRuneEmbedded = presentRune.toEmbedded()
    val futureRuneEmbedded = futureRune.toEmbedded()
    timelineItemDao.insertPpfRuneReading(
      timelineItemEntity = timelineItemEntity,
      pastRune =pastRuneEmbedded,
      presentRune = presentRuneEmbedded,
      futureRune = futureRuneEmbedded
    )
  }

  override suspend fun updateTimelineItem(id: Long, notes: String?, imageFileName: String?): Boolean {
    val rowsUpdated = timelineItemDao.updateTimelineItem(id, notes, imageFileName)
    return rowsUpdated > 0
  }

  override suspend fun deleteTimelineItem(id: Long): Boolean {
    val rowsDeleted = timelineItemDao.deleteTimelineItem(id)
    return rowsDeleted > 0
  }


}