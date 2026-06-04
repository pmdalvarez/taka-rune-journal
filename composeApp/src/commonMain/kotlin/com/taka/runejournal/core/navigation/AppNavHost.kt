package com.taka.runejournal.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.taka.runejournal.feature.timeline.ui.TimelineScreen
import com.taka.runejournal.core.ui.AboutScreen
import com.taka.runejournal.feature.reading.ui.NewReadingDrawScreen
import com.taka.runejournal.feature.reading.ui.NewReadingInterpretationScreen
import com.taka.runejournal.feature.reading.ui.NewReadingStartScreen
import com.taka.runejournal.feature.reading.ui.ReadingViewModel
import com.taka.runejournal.feature.settings.ui.SettingsScreen
import com.taka.runejournal.feature.settings.ui.SettingsViewModel
import com.taka.runejournal.feature.timeline.ui.EditTimelineItemScreen
import com.taka.runejournal.feature.timeline.ui.NewJournalEntryScreen
import com.taka.runejournal.feature.timeline.ui.TimelineDetailScreen
import com.taka.runejournal.feature.timeline.ui.TimelineViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Timeline.value
    ) {
        composable(Route.Timeline.value) {
            val viewModel = koinViewModel<TimelineViewModel>()
            TimelineScreen(
                viewModel,
                onAboutClick = {
                    navController.navigate(Route.About.value)
                },
                onTimelineDetailClick = { id -> navController.navigate("${Route.TimelineDetail.value}/$id") },
                onNewJournalEntryClick = {
                    navController.navigate(Route.NewJournalEntry.value)
                },
                onNewReadingClick = {
                    navController.navigate(Route.NewReadingGraph.value)
                }
            )
        }

        composable(Route.TimelineDetail.value) {
            val viewModel = koinViewModel<TimelineViewModel>()
            TimelineDetailScreen(
                viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.NewJournalEntry.value) {
            val viewModel = koinViewModel<TimelineViewModel>()
            NewJournalEntryScreen(
                viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaved = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.EditTimelineItem.value) {
            val viewModel = koinViewModel<TimelineViewModel>()
            EditTimelineItemScreen(
                viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaved = {
                    navController.popBackStack()
                }
            )
        }

        navigation(
            route = Route.NewReadingGraph.value,
            startDestination = Route.NewReadingStart.value,
        ) {
            composable(Route.NewReadingStart.value) {backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Route.NewReadingGraph.value)
                }

                val viewModel = koinViewModel<ReadingViewModel>(
                    viewModelStoreOwner = parentEntry,
                )

                NewReadingStartScreen(
                    viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Route.NewReadingDraw.value) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Route.NewReadingGraph.value)
                }

                val viewModel = koinViewModel<ReadingViewModel>(
                    viewModelStoreOwner = parentEntry,
                )

                NewReadingDrawScreen(
                    viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Route.NewReadingInterpretation.value) {backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Route.NewReadingGraph.value)
                }

                val viewModel = koinViewModel<ReadingViewModel>(
                    viewModelStoreOwner = parentEntry,
                )

                NewReadingInterpretationScreen(
                    viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onReadingFinished = {
                        navController.popBackStack(
                            route = Route.Timeline.value,
                            inclusive = false
                        )
                    }
                )
            }
        }

        composable(Route.Settings.value) {
            val viewModel = koinViewModel<SettingsViewModel>()
            SettingsScreen(
                viewModel
            )
        }

        composable(Route.About.value) {
            AboutScreen()
        }
    }
}