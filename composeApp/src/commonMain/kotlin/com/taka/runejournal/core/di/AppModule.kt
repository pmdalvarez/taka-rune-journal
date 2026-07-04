package com.taka.runejournal.core.di

import com.taka.runejournal.feature.reading.ui.NewReadingViewModel
import com.taka.runejournal.feature.more.data.repository.DataStoreSettingsRepository
import com.taka.runejournal.feature.more.domain.repository.SettingsRepository
import com.taka.runejournal.feature.more.ui.SettingsViewModel
import com.taka.runejournal.feature.reading.ui.ReadingInterpretationViewModel
import com.taka.runejournal.feature.timeline.data.repository.DatabaseTimelineRepository
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import com.taka.runejournal.feature.timeline.ui.JournalEntryDetailViewModel
import com.taka.runejournal.feature.timeline.ui.NewJournalEntryViewModel
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
      settingsRepository = get()
    )
  }

  viewModel { (id: Long) ->
    JournalEntryDetailViewModel(
      id = id,
      timelineRepository = get()
    )
  }


  viewModel { (id: Long) ->
    ReadingInterpretationViewModel(
      id = id,
      timelineRepository = get()
    )
  }


  viewModel {
    NewJournalEntryViewModel(
      timelineRepository = get()
    )
  }

  viewModel {
    SettingsViewModel(
      repository = get(),
    )
  }

  viewModel {
    NewReadingViewModel(
      settingsRepository = get(),
      timelineRepository = get()
    )
  }

}