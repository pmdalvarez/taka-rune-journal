package com.taka.runejournal.feature.more.ui

import com.taka.runejournal.feature.timeline.ui.TimelineUiEvent
import org.jetbrains.compose.resources.StringResource

sealed class SettingsUiEvent {
  data class ShowError(val messageRes: StringResource) : SettingsUiEvent()
}