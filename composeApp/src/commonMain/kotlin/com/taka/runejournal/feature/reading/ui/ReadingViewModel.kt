package com.taka.runejournal.feature.reading.ui

import androidx.lifecycle.ViewModel
import com.taka.runejournal.feature.settings.domain.repository.SettingsRepository
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository

class ReadingViewModel(
  private val settingsRepository: SettingsRepository,
  private val timelineRepository: TimelineRepository
) : ViewModel() {
}