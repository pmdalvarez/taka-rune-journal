package com.taka.runejournal.feature.timeline.ui

import org.jetbrains.compose.resources.StringResource

sealed class TimelineUiEvent {
  data class ShowInfo(val messageRes: StringResource) : TimelineUiEvent()
  data class ShowError(val messageRes: StringResource) : TimelineUiEvent()
}