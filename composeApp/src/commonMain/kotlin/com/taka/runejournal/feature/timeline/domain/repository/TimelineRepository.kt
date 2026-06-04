package com.taka.runejournal.feature.timeline.domain.repository

import androidx.paging.PagingData
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingCategory
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import kotlinx.coroutines.flow.Flow

interface TimelineRepository {

  fun observeTimelineItems(): Flow<PagingData<TimelineItem>>

  suspend fun getTimelineItem(id: Long): TimelineItem?

  suspend fun createJournalEntry(notes: String, title: String?)

  suspend fun createSingleRuneReading(question: String?, category: ReadingCategory, rune: DrawnRune, notes: String?)

  suspend fun createPpfRuneReading(
    question: String?,
    category: ReadingCategory,
    pastRune: DrawnRune,
    presentRune: DrawnRune,
    futureRune: DrawnRune,
    notes: String?
  )

  suspend fun updateTimelineItem(id: Long, notes: String?, title: String?): Boolean

  suspend fun deleteTimelineItem(id: Long): Boolean

}