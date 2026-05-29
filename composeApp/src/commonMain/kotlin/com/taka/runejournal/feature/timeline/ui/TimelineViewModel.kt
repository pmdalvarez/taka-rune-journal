package com.taka.runejournal.feature.timeline.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.launch

class TimelineViewModel(private val repository: TimelineRepository) : ViewModel() {

  val timelineItems = repository.observeTimelineItems().cachedIn(viewModelScope)

  // TODO: show add dialog
  // TODO: show delete confirmation dialog
  // TODO: show edit dialog

  // TODO: close delete dialog
  // TODO: close add dialog
  // TODO: close edit dialog

  fun addJournalEntry(id: Long, notes: String) {
    val imageFileName = null // TODO: implement image upload to journal entries
    viewModelScope.launch {
      repository.addJournalEntry(notes, imageFileName)
      // TODO: Update UI state to show success
    }
  }

  fun deleteTimelineItem(id: Long) {
    viewModelScope.launch {
      val isDeleted = repository.deleteTimelineItem(id)
      if (isDeleted) {
        // TODO: Update UI state to show success
      } else {
        // TODO: Update UI state to show error message
      }
    }
  }

  fun updateTimelineItem(id: Long, notes: String) {
    val imageFileName = null // TODO: implement image upload to journal entries
    viewModelScope.launch {
      val isDeleted = repository.updateTimelineItem(id, notes, imageFileName)
      if (isDeleted) {
        // TODO: Update UI state to show success
      } else {
        // TODO: Update UI state to show error message
      }
    }
  }

}