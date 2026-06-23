package com.taka.runejournal.feature.timeline.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingCategory
import com.taka.runejournal.core.domain.model.RuneId
import com.taka.runejournal.core.domain.model.RuneOrientation
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.feature.more.domain.repository.SettingsRepository
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.new_journal_entry_save_error_blank_notes
import taka_rune_journal.composeapp.generated.resources.timeline_delete_dialog_error
import taka_rune_journal.composeapp.generated.resources.timeline_delete_dialog_success
import kotlin.random.Random
import kotlin.time.Clock

class TimelineViewModel(
  private val timelineRepository: TimelineRepository,
  private val settingsRepository: SettingsRepository,
) : ViewModel() {

  val timelineItems = timelineRepository.observeTimelineItems().map { pagingData ->
      pagingData.map { it.toUiModel() }
    }.cachedIn(viewModelScope)

  private val _uiState = MutableStateFlow(TimelineUiState())
  val uiState: StateFlow<TimelineUiState> = _uiState.asStateFlow()

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  init {
    viewModelScope.launch {
      settingsRepository.displayName.collect { displayName ->
        _uiState.update {
          it.copy(displayName = displayName)
        }
      }
    }
  }

  fun setDisplayName(displayName: String) {
    viewModelScope.launch {
      settingsRepository.setDisplayName(displayName)
    }
  }

  fun initializeDailyPrompt(prompts: List<String>) {
    if (prompts.isEmpty() || _uiState.value.dailyPrompt != null) return
    // todayInDays = number of days since epoch, used as seed for random prompt to ensure same index for same day
    val todayInDays = Clock.System.now().epochSeconds / (24 * 60 * 60)
    val randomPromptIndex = Random(todayInDays).nextInt(prompts.size)
    _uiState.update {
      it.copy(dailyPrompt = prompts[randomPromptIndex])
    }
  }

  fun openDeleteDialog(id: Long, title: String?, type: String) {
    _uiState.update { it.copy(deleteDialogUiState = DeleteTimelineItemDialogUiState(id, title, type)) }
  }

  fun dismissDeleteDialog() {
    _uiState.update { it.copy(deleteDialogUiState = null) }
  }

  fun deleteTimelineItem() {
    viewModelScope.launch {
      val isDeleted = _uiState.value.deleteDialogUiState?.let {
        timelineRepository.deleteTimelineItem(it.id)
      } ?: false
      if (isDeleted) {
        _uiEvent.emit(UiEvent.ShowInfo(Res.string.timeline_delete_dialog_success))
      } else {
        _uiEvent.emit(UiEvent.ShowError(Res.string.timeline_delete_dialog_error))
      }
      dismissDeleteDialog() // close dialog regardless if delete succeeded
    }
  }

  // TODO: Remove
  fun createSingleRuneReading() {
    viewModelScope.launch {
      timelineRepository.createSingleRuneReading(
        question = "Should I take this job?",
        category = ReadingCategory.PURPOSE,
        rune = DrawnRune(RuneId.GEBO, orientation = RuneOrientation.UPRIGHT),
        notes = "This reading was not so clear to me."
      )
      _uiEvent.emit(UiEvent.NavigateBack)
    }
  }

  // TODO: Remove
  fun createPpfRuneReading() {
    viewModelScope.launch {
      timelineRepository.createPpfRuneReading(
        question = "Where should i live next year?",
        category = ReadingCategory.PURPOSE,
        pastRune = DrawnRune(RuneId.THURISAZ, orientation = RuneOrientation.UPRIGHT),
        presentRune = DrawnRune(RuneId.WUNJO, orientation = RuneOrientation.UPRIGHT),
        futureRune = DrawnRune(RuneId.ALGIZ, orientation = RuneOrientation.REVERSED),
        notes = "I was very inspired by this reading."
      )
      _uiEvent.emit(UiEvent.NavigateBack)
    }
  }

  // TODO: Remove
  fun updateTimelineItem(id: Long, notes: String, title: String?) {
    viewModelScope.launch {
      val isDeleted = timelineRepository.updateTimelineItem(id = id, notes = notes, title = title)
      if (isDeleted) {
        // TODO: Update UI state to show success
      } else {
        // TODO: Update UI state to show error message
      }
    }
  }

}