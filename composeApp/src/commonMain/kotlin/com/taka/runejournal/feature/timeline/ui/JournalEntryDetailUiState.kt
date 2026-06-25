package com.taka.runejournal.feature.timeline.ui

import kotlin.time.Instant

enum class JournalEntryDetailMode { isViewing, isDeleting, isEditing, isSaving }

data class JournalEntryDetailUiState(
  val id: Long = 0L,
  val formattedDate: String = "",
  val title: String? = null,
  val notes: String = "",
  val mode: JournalEntryDetailMode = JournalEntryDetailMode.isViewing,
)