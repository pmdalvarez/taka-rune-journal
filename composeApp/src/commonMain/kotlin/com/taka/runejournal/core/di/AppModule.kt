package com.taka.runejournal.core.di

import com.taka.runejournal.feature.settings.data.repository.DataStoreSettingsRepository
import com.taka.runejournal.feature.settings.domain.repository.SettingsRepository
import com.taka.runejournal.feature.timeline.data.repository.DatabaseTimelineRepository
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import com.taka.runejournal.feature.timeline.ui.TimelineViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

  single<SettingsRepository> {
    DataStoreSettingsRepository(
      dataStore = get(),
    )
  }

  single<TimelineRepository> {
    DatabaseTimelineRepository(
      timelineItemDao = get(),
    )
  }

  viewModel {
    TimelineViewModel(
      timelineRepository = get(),
      settingsRepository = get(),
    )
  }
}