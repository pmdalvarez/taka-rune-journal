package com.taka.runejournal.feature.timeline.ui

import kotlin.time.Instant

data class TimelineItemUiModel(
  val id: Long,
  val createdAt: Instant,
  val notes: String?

)
