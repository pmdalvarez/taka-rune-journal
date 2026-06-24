package com.taka.runejournal.feature.timeline.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.journal_entry_save_error_blank_notes

class NewJournalEntryViewModel(
  private val timelineRepository: TimelineRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow(NewJournalEntryUiState())
  val uiState: StateFlow<NewJournalEntryUiState> = _uiState.asStateFlow()

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  fun createJournalEntry(notes: String, title: String?) {
    _uiState.update {
      it.copy(isSaving = true)
    }
    viewModelScope.launch {
      if (notes.isBlank()) {
        _uiState.update {
          it.copy(isSaving = false)
        }
        _uiEvent.emit(UiEvent.ShowError(Res.string.journal_entry_save_error_blank_notes))
        return@launch
      }
      timelineRepository.createJournalEntry(notes, title)
      _uiEvent.emit(UiEvent.NavigateBack)
    }
  }

}
