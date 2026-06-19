package com.taka.runejournal.feature.timeline.ui

import org.jetbrains.compose.resources.StringResource

sealed class NewJournalEntryUiEvent{
  data object NavigateBack : NewJournalEntryUiEvent()
  data class ShowError(val messageRes: StringResource) : NewJournalEntryUiEvent()
}