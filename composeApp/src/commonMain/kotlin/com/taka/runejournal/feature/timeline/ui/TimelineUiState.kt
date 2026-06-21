package com.taka.runejournal.feature.timeline.ui

data class TimelineUiState(
  val displayName: String? = null,
  val dailyPrompt: String? = null,
  val deleteDialogUiState: DeleteTimelineItemDialogUiState? = null,
)

