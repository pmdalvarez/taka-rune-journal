package com.taka.runejournal.feature.timeline.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.taka.runejournal.feature.settings.domain.repository.SettingsRepository
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Clock

class TimelineViewModel(
  private val timelineRepository: TimelineRepository,
  private val settingsRepository: SettingsRepository,
) : ViewModel() {

  val timelineItems = timelineRepository.observeTimelineItems().cachedIn(viewModelScope)

  private val _uiState = MutableStateFlow(TimelineUiState())
  val uiState: StateFlow<TimelineUiState> = _uiState.asStateFlow()

  init {
    viewModelScope.launch {
      settingsRepository.displayName.collect { displayName ->
        _uiState.update {
          it.copy(displayName = displayName)
        }
      }
    }
  }
  // TODO: show add dialog
  // TODO: show delete confirmation dialog
  // TODO: show edit dialog

  // TODO: close delete dialog
  // TODO: close add dialog
  // TODO: close edit dialog

  fun setDisplayName(displayName: String) {
    viewModelScope.launch {
      settingsRepository.setDisplayName(displayName)
      // TODO: Update UI state to show success
    }
  }

  fun setDailyPrompt(prompts: List<String>) {
    if (prompts.isEmpty() || _uiState.value.prompt != null) return
    // todayInDays = number of days since epoch, used as seed for random prompt to ensure same index for same day
    val todayInDays = Clock.System.now().epochSeconds / (24 * 60 * 60)
    val randomPromptIndex = Random(todayInDays).nextInt(prompts.size)
    _uiState.update {
      it.copy(prompt = prompts[randomPromptIndex])
    }
  }

  fun createJournalEntry(id: Long, notes: String) {
    val imageFileName = null // TODO: implement image upload to journal entries
    viewModelScope.launch {
      timelineRepository.createJournalEntry(notes, imageFileName)
      // TODO: Update UI state to show success
    }
  }

  fun deleteTimelineItem(id: Long) {
    viewModelScope.launch {
      val isDeleted = timelineRepository.deleteTimelineItem(id)
      if (isDeleted) {
        // TODO: Update UI state to show success
      } else {
        // TODO: Update UI state to show error message
      }
    }
  }

  fun updateTimelineItem(id: Long, notes: String, title: String?) {
    val imageFileName = null // TODO: implement image upload to journal entries
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