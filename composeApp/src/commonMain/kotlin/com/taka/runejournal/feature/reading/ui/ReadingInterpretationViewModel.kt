package com.taka.runejournal.feature.reading.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingTopic
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
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_position_future_description
import taka_rune_journal.composeapp.generated.resources.reading_position_past_description
import taka_rune_journal.composeapp.generated.resources.reading_position_present_description
import taka_rune_journal.composeapp.generated.resources.reading_load_error
import taka_rune_journal.composeapp.generated.resources.reading_notes_save_error
import taka_rune_journal.composeapp.generated.resources.reading_tab_future_rune
import taka_rune_journal.composeapp.generated.resources.reading_tab_notes
import taka_rune_journal.composeapp.generated.resources.reading_tab_past_rune
import taka_rune_journal.composeapp.generated.resources.reading_tab_present_rune
import taka_rune_journal.composeapp.generated.resources.reading_tab_single_rune
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
            topic = timelineItem.topic,
            question = timelineItem.question,
            tabs = listOf(
              getRuneTab(timelineItem.drawnRune, Res.string.reading_tab_single_rune, timelineItem.topic),
              ReadingInterpretationTab.Notes(Res.string.reading_tab_notes, timelineItem.notes)
            )
          )
        }
        is TimelineItem.PpfRuneReading -> {
          _uiState.value = ReadingInterpretationUiState(
            id = timelineItem.id,
            createdAt = timelineItem.createdAt.format(),
            topic = timelineItem.topic,
            question = timelineItem.question,
            tabs = listOf(
              getRuneTab(
                timelineItem.pastRune,
                Res.string.reading_tab_past_rune,
                timelineItem.topic,
                Res.string.reading_position_past_description
              ),
              getRuneTab(
                timelineItem.presentRune,
                Res.string.reading_tab_present_rune,
                timelineItem.topic,
                Res.string.reading_position_present_description
              ),
              getRuneTab(
                timelineItem.futureRune,
                Res.string.reading_tab_future_rune,
                timelineItem.topic,
                Res.string.reading_position_future_description
              ),
              ReadingInterpretationTab.Notes(Res.string.reading_tab_notes, timelineItem.notes)
            )
          )
        }
        else -> {
          _uiEvent.emit(UiEvent.ShowError(Res.string.reading_load_error))
          return@launch
        }
      }
    }
  }

  private fun getRuneTab(
    drawnRune: DrawnRune,
    tabName: StringResource,
    topic: ReadingTopic,
    tabDescription: StringResource? = null
  ): ReadingInterpretationTab.Rune {
    return ReadingInterpretationTab.Rune(
      label = tabName,
      drawnRune = drawnRune,
      interpretation = drawnRune.generalInterpretation(),
      supplementalInterpretation = drawnRune.supplementalInterpretation(topic),
      keywords = drawnRune.generalKeywords(),
      supplementalKeywords = drawnRune.supplementalKeywords(topic),
      tabDescription = tabDescription
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

  fun saveNotes(notes: String) {
    viewModelScope.launch {
      val isSaved = timelineRepository.updateTimelineItem(
        id = id,
        notes = notes,
        title = null
      )
      if (isSaved) {
        _uiState.update {
          it.copy(
            tabs = it.tabs.map { tab ->
              if (tab is ReadingInterpretationTab.Notes)
                tab.copy(notes = notes)
              else
                tab
            }
          )
        }
      } else {
        _uiEvent.emit(UiEvent.ShowError(Res.string.reading_notes_save_error))
      }
    }
  }

}