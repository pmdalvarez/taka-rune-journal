package com.taka.runejournal.feature.timeline.domain.model

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingCategory
import kotlin.time.Instant

sealed class TimelineItem {
  abstract val id: Long
  abstract val createdAt: Instant
  abstract val notes: String?

  data class JournalEntry(
    override val id: Long,
    override val createdAt: Instant,
    override val notes: String?,
    val title: String?
  ) : TimelineItem()

  data class SingleRuneReading(
    override val id: Long,
    override val createdAt: Instant,
    override val notes: String?,
    val question: String?,
    val category: ReadingCategory,
    val rune: DrawnRune,
  ) : TimelineItem()

  data class PpfRuneReading(
    override val id: Long,
    override val createdAt: Instant,
    override val notes: String?,
    val question: String?,
    val category: ReadingCategory,
    val pastRune: DrawnRune,
    val presentRune: DrawnRune,
    val futureRune: DrawnRune,
  ) : TimelineItem()

}