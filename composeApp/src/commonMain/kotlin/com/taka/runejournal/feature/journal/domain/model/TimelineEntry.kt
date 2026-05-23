package com.taka.runejournal.feature.journal.domain.model

import com.taka.runejournal.core.domain.model.DrawnRune
import kotlin.time.Instant

sealed class TimelineEntry {
  abstract val id: Int
  abstract val createdAt: Instant

  data class JournalEntry(
    override val id: Int,
    override val createdAt: Instant,
    val text: String,
    val imageFileName: String?
  ) : TimelineEntry()

  data class SingleRuneReading(
    override val id: Int,
    override val createdAt: Instant,
    val rune: DrawnRune,
    val notes: String?
  ) : TimelineEntry()

  data class PpfRuneReading(
    override val id: Int,
    override val createdAt: Instant,
    val pastRune: DrawnRune,
    val presentRune: DrawnRune,
    val futureRune: DrawnRune,
    val notes: String?
  ) : TimelineEntry()


}