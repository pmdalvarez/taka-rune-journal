package com.taka.runejournal.core.ui

import org.jetbrains.compose.resources.StringResource

sealed class UiEvent {
  data object NavigateBack : UiEvent()
  data object NavigateForward : UiEvent()
  data class NavigateToItem(val itemId: Long) : UiEvent()

  data class ShowInfo(val messageRes: StringResource) : UiEvent()
  data class ShowError(val messageRes: StringResource) : UiEvent()
}