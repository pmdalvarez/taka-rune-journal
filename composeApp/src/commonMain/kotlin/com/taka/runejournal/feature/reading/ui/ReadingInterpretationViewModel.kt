package com.taka.runejournal.feature.reading.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingPosition
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.utils.format
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
import taka_rune_journal.composeapp.generated.resources.rune_reading_load_error
import taka_rune_journal.composeapp.generated.resources.timeline_delete_dialog_error

class ReadingInterpretationViewModel(
  private val id: Long,
  private val timelineRepository: TimelineRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow(ReadingInterpretationUiState())
  val uiState: StateFlow<ReadingInterpretationUiState> = _uiState.asStateFlow()

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  init {
    loadReading()
  }

  private fun loadReading() {
    viewModelScope.launch {
      val timelineItem = timelineRepository.getTimelineItem(id)
      when (timelineItem) {
        is TimelineItem.SingleRuneReading -> {
          _uiState.value = ReadingInterpretationUiState(
            id = timelineItem.id,
            createdAt = timelineItem.createdAt.format(),
            notes = timelineItem.notes,
            category = timelineItem.category,
            question = timelineItem.question,
            runeInterpretations = listOf(getRuneInterpretation(timelineItem.rune, ReadingPosition.SINGLE))
          )
        }
        is TimelineItem.PpfRuneReading -> {
          _uiState.value = ReadingInterpretationUiState(
            id = timelineItem.id,
            createdAt = timelineItem.createdAt.format(),
            notes = timelineItem.notes,
            category = timelineItem.category,
            question = timelineItem.question,
            runeInterpretations = listOf(
              getRuneInterpretation(timelineItem.pastRune, ReadingPosition.PAST),
              getRuneInterpretation(timelineItem.presentRune, ReadingPosition.PRESENT),
              getRuneInterpretation(timelineItem.futureRune, ReadingPosition.FUTURE)
            )
          )
        }
        else -> {
          _uiEvent.emit(UiEvent.ShowError(Res.string.rune_reading_load_error))
          return@launch
        }
      }
    }
  }

  private fun getRuneInterpretation(rune: DrawnRune, position: ReadingPosition): RuneInterpretation {
    // TODO - interpretation may need to be done inside composable functions
    return RuneInterpretation(
      position = position,
      rune = rune,
      interpretation = "Place interpretation here"
    )
  }

  fun openDeleteDialog() {
    _uiState.update { it.copy(showDeleteDialog = true) }
  }

  fun dismissDeleteDialog() {
    _uiState.update { it.copy(showDeleteDialog = false) }
  }

  fun deleteReading() {
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