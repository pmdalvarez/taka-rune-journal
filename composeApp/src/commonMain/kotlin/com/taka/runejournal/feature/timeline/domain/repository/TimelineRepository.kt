package com.taka.runejournal.feature.timeline.domain.repository

import androidx.paging.PagingData
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import kotlinx.coroutines.flow.Flow

interface TimelineRepository {

  fun observePagedTimelineItems(): Flow<PagingData<TimelineItem>>

  suspend fun addJournalEntry(notes: String, imageFileName: String?)

  suspend fun addSingleRuneReading(rune: DrawnRune, notes: String?)

  suspend fun addPpfRuneReading(pastRune: DrawnRune, presentRune: DrawnRune, futureRune: DrawnRune, notes: String?)

  suspend fun updateTimelineItem(id: Long, notes: String?, imageFileName: String?)

  suspend fun deleteTimelineitem(id: Long)

}