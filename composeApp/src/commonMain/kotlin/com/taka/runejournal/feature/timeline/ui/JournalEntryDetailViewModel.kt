package com.taka.runejournal.feature.timeline.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.journal_entry_load_error
import taka_rune_journal.composeapp.generated.resources.timeline_delete_dialog_error

class JournalEntryDetailViewModel (
  private val timelineItemId: Long,
  private val timelineRepository: TimelineRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow(JournalEntryDetailUiState())
  val uiState: StateFlow<JournalEntryDetailUiState> = _uiState.asStateFlow()

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  init {
    loadJournalEntry()
  }

  private fun loadJournalEntry() {
    viewModelScope.launch {
      val timelineItem = timelineRepository.getTimelineItem(timelineItemId)
      if (!(timelineItem is TimelineItem.JournalEntry)) {
        _uiEvent.emit(UiEvent.ShowError(Res.string.journal_entry_load_error))
        return@launch
      }
      _uiState.value = JournalEntryDetailUiState(
        id = timelineItem.id,
        createdAt = timelineItem.createdAt,
        title = timelineItem.title,
        notes = timelineItem.notes ?: ""
      )
    }
  }

  fun openDeleteDialog() {
    _uiState.update { it.copy(showDeleteDialog = true) }
  }

  fun dismissDeleteDialog() {
    _uiState.update { it.copy(showDeleteDialog = false) }
  }

  fun deleteJournalEntry() {
    viewModelScope.launch {
      val isDeleted = timelineRepository.deleteTimelineItem(_uiState.value.id)
      if (isDeleted) {
        _uiEvent.emit(UiEvent.NavigateBack)
      } else {
        _uiEvent.emit(UiEvent.ShowError(Res.string.timeline_delete_dialog_error))
      }
      dismissDeleteDialog() // close dialog regardless if delete succeeded
    }
  }


}