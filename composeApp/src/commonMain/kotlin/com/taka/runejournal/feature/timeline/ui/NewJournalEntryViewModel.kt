package com.taka.runejournal.feature.timeline.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.launch

class NewJournalEntryViewModel(
  private val timelineRepository: TimelineRepository
) : ViewModel() {

  fun createJournalEntry(notes: String, title: String?) {
    viewModelScope.launch {
      timelineRepository.createJournalEntry(notes, title)
      // TODO: Update UI state to show success
    }
  }

}
