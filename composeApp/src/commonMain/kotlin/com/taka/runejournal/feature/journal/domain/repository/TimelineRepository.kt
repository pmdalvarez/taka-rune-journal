package com.taka.runejournal.feature.journal.domain.repository

import androidx.paging.PagingData
import com.taka.runejournal.feature.journal.domain.model.TimelineItem
import kotlinx.coroutines.flow.Flow

interface TimelineRepository {

  fun observePagedTimelineItems(): Flow<PagingData<TimelineItem>>

  suspend fun addJournalEntry(journalEntry: TimelineItem.JournalEntry)

  suspend fun addSingleRuneReading(reading: TimelineItem.SingleRuneReading)

  suspend fun addPpfRuneReading(reading: TimelineItem.PpfRuneReading)

  suspend fun editTimelineItem(id: Int, notes: String)

  suspend fun deleteTimelineitem(id: Int)

}