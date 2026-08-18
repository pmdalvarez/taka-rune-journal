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
import com.taka.runejournal.feature.reading.navigation.NewReadingFlowNavDisplay
import com.taka.runejournal.feature.more.ui.SettingsScreen
import com.taka.runejournal.feature.more.ui.SettingsViewModel
import com.taka.runejournal.feature.reading.ui.ReadingInterpretationScreen
import com.taka.runejournal.feature.reading.ui.ReadingInterpretationViewModel
import com.taka.runejournal.feature.timeline.ui.NewJournalEntryScreen
import com.taka.runejournal.feature.timeline.ui.NewJournalEntryViewModel
import com.taka.runejournal.feature.timeline.ui.JournalEntryDetailScreen
import com.taka.runejournal.feature.timeline.ui.JournalEntryDetailViewModel
import com.taka.runejournal.feature.timeline.ui.TimelineScreen
import com.taka.runejournal.feature.timeline.ui.TimelineViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

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
                    onJournalEntryClick = { id ->
                        backStack.add(
                            JournalEntryDetailRoute(id = id)
                        )
                    },
                    onRuneReadingClick = { id ->
                        backStack.add(
                            ReadingInterpretationRoute(id = id)
                        )
                    },
                    onNewReadingClick = {
                        backStack.add(NewReadingFlowRoute)
                    },
                    onNewJournalEntryClick = {
                        backStack.add(NewJournalEntryRoute)
                    },
                    modifier = modifier
                )
            }

            entry<JournalEntryDetailRoute> { route ->
                val viewModel = koinViewModel<JournalEntryDetailViewModel>(
                    key = "journal-entry-detail-${route.id}",
                    parameters = {
                        parametersOf(route.id)
                    },
                )

                JournalEntryDetailScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        backStack.removeLastOrNull()
                    },
                    modifier = modifier
                )
            }

            entry<ReadingInterpretationRoute> { route ->
                val viewModel = koinViewModel<ReadingInterpretationViewModel>(
                    key = "reading-interpretation-${route.id}",
                    parameters = {
                        parametersOf(route.id)
                    },
                )

                ReadingInterpretationScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        backStack.removeLastOrNull()
                    },
                    modifier = modifier
                )
            }

            entry<NewJournalEntryRoute> {
                val viewModel = koinViewModel<NewJournalEntryViewModel>()

                NewJournalEntryScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        backStack.removeLastOrNull()
                    },
                    modifier = modifier
                )
            }

            entry<NewReadingFlowRoute> {
                NewReadingFlowNavDisplay(
                    onExitReadingFlow = {
                        backStack.removeLastOrNull()
                    },
                    onNavigateToReadingInterpretation = { id ->
                        backStack.removeLastOrNull()
                        backStack.add(ReadingInterpretationRoute(id = id))
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