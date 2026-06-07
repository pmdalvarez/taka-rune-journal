package com.taka.runejournal.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.taka.runejournal.feature.reading.navigation.ReadingFlowNavDisplay
import com.taka.runejournal.feature.settings.ui.SettingsScreen
import com.taka.runejournal.feature.settings.ui.SettingsViewModel
import com.taka.runejournal.feature.timeline.ui.NewJournalEntryScreen
import com.taka.runejournal.feature.timeline.ui.TimelineDetailScreen
import com.taka.runejournal.feature.timeline.ui.TimelineScreen
import com.taka.runejournal.feature.timeline.ui.TimelineViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavDisplay() {
    val backStack = rememberNavBackStack(appNavSavedStateConfiguration, TimelineRoute)

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<TimelineRoute> {
                val viewModel = koinViewModel<TimelineViewModel>()

                TimelineScreen(
                    viewModel = viewModel,
                    onSettingsClick = {
                        backStack.add(SettingsRoute)
                    },
                    onTimelineDetailClick = { id ->
                        backStack.add(
                            TimelineDetailRoute(timelineItemId = id)
                        )
                    },
                    onNewReadingClick = {
                        backStack.add(ReadingFlowRoute)
                    },
                    onNewJournalEntryClick = {
                        backStack.add(NewJournalEntryRoute)
                    }
                )
            }

            entry<TimelineDetailRoute> { route ->
                val viewModel = koinViewModel<TimelineViewModel>()

                TimelineDetailScreen(
                    viewModel = viewModel,
                    timelineItemId = route.timelineItemId,
                    onBackClick = {
                        backStack.removeLastOrNull()
                    },
                    onSaved = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<NewJournalEntryRoute> {
                val viewModel = koinViewModel<TimelineViewModel>()

                NewJournalEntryScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        backStack.removeLastOrNull()
                    },
                    onSaved = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<ReadingFlowRoute> {
                ReadingFlowNavDisplay(
                    onExitReadingFlow = {
                        backStack.removeLastOrNull()
                    },
                    onReadingFinished = {
                        backStack.removeLastOrNull()
                    },
                )
            }

            entry<SettingsRoute> {
                val viewModel = koinViewModel<SettingsViewModel>()

                SettingsScreen(
                    viewModel = viewModel,
                )
            }

        },
    )
}