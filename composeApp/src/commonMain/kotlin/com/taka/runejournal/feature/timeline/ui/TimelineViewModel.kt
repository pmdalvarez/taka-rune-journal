package com.taka.runejournal.feature.timeline.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.taka.runejournal.feature.settings.domain.repository.SettingsRepository
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TimelineViewModel(
  private val timelineRepository: TimelineRepository,
  private val settingsRepository: SettingsRepository,
) : ViewModel() {

  val timelineItems = timelineRepository.observeTimelineItems().cachedIn(viewModelScope)

  val uiState: StateFlow<TimelineUiState> =
    settingsRepository.displayName
      .map { displayName ->
        TimelineUiState(
          displayName = displayName,
        )
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TimelineUiState(),
      )

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

  fun addJournalEntry(id: Long, notes: String) {
    val imageFileName = null // TODO: implement image upload to journal entries
    viewModelScope.launch {
      timelineRepository.addJournalEntry(notes, imageFileName)
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