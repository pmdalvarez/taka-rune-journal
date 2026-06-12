package com.taka.runejournal.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.taka.runejournal.core.ui.DesignPlaygroundScreen
import com.taka.runejournal.feature.more.ui.AboutScreen
import com.taka.runejournal.feature.reading.navigation.ReadingFlowNavDisplay
import com.taka.runejournal.feature.more.ui.SettingsScreen
import com.taka.runejournal.feature.more.ui.SettingsViewModel
import com.taka.runejournal.feature.timeline.ui.NewJournalEntryScreen
import com.taka.runejournal.feature.timeline.ui.TimelineDetailScreen
import com.taka.runejournal.feature.timeline.ui.TimelineScreen
import com.taka.runejournal.feature.timeline.ui.TimelineViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavDisplay(modifier: Modifier = Modifier) {
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
                    onAboutClick = {
                        backStack.add(AboutRoute)
                    },
                    onSettingsClick = {
                        backStack.add(SettingsRoute)
                    },
                    onDesignPlaygroundClick = {
                        backStack.add(DesignSystemRoute)
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
                    },
                    modifier = modifier
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
                    },
                    modifier = modifier
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
                    },
                    modifier = modifier
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
                    modifier = modifier
                )
            }

            entry<SettingsRoute> {
                val viewModel = koinViewModel<SettingsViewModel>()

                SettingsScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        backStack.removeLastOrNull()
                    },
                    modifier = modifier
                )
            }

            entry<AboutRoute> {
                AboutScreen(
                    onBackClick = {
                        backStack.removeLastOrNull()
                    },
                    modifier = modifier
                )
            }

            entry<DesignSystemRoute> {
                DesignPlaygroundScreen(
                    onBackClick = {
                        backStack.removeLastOrNull()
                    },
                    modifier = modifier
                )
            }

        },
    )
}