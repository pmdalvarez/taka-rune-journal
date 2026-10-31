package com.taka.runejournal.feature.timeline.domain.repository

import androidx.paging.PagingData
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import kotlinx.coroutines.flow.Flow
import kotlin.time.Clock
import kotlin.time.Instant

interface TimelineRepository {

  fun observeTimelineItems(): Flow<PagingData<TimelineItem>>

  suspend fun getTimelineItem(id: Long): TimelineItem?

  suspend fun createJournalEntry(notes: String, title: String?)

  suspend fun createSingleRuneReading(
    recipient: String?,
    question: String?,
    topic: ReadingTopic,
    rune: DrawnRune,
    createdAt: Instant = Clock.System.now()
  ): Long

  suspend fun createPpfRuneReading(
    recipient: String?,
    question: String?,
    topic: ReadingTopic,
    pastRune: DrawnRune,
    presentRune: DrawnRune,
    futureRune: DrawnRune,
    createdAt: Instant = Clock.System.now()
  ): Long

  suspend fun updateTimelineItem(id: Long, notes: String?, title: String?): Boolean

  suspend fun deleteTimelineItem(id: Long): Boolean

}