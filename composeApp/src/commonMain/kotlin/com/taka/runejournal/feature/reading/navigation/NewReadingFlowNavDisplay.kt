package com.taka.runejournal.feature.reading.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.taka.runejournal.core.navigation.appNavSavedStateConfiguration
import com.taka.runejournal.feature.reading.ui.NewReadingDrawScreen
import com.taka.runejournal.feature.reading.ui.NewReadingStartScreen
import com.taka.runejournal.feature.reading.ui.NewReadingViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewReadingFlowNavDisplay(
  onExitReadingFlow: () -> Unit,
  onNavigateToReadingInterpretation: (id: Long) -> Unit,
  modifier: Modifier = Modifier
) {
  val readingBackStack = rememberNavBackStack(appNavSavedStateConfiguration, NewReadingStartRoute)

  val viewModel = koinViewModel<NewReadingViewModel>()

  NavDisplay(
    backStack = readingBackStack,
    onBack = {
      if (readingBackStack.size > 1) {
        readingBackStack.removeLastOrNull()
      } else {
        onExitReadingFlow()
      }
    },
    entryDecorators = listOf(
      rememberSaveableStateHolderNavEntryDecorator(),
      rememberViewModelStoreNavEntryDecorator(),
    ),
    entryProvider = entryProvider {
      entry<NewReadingStartRoute> {
        NewReadingStartScreen(
          viewModel = viewModel,
          onBackClick = {
            onExitReadingFlow()
          },
          onContinueClick = {
            readingBackStack.add(NewReadingDrawRoute)
          },
          modifier = modifier
        )
      }

      entry<NewReadingDrawRoute> {
        NewReadingDrawScreen(
          viewModel = viewModel,
          onBackClick = {
            readingBackStack.removeLastOrNull()
          },
          onNavigateToReadingInterpretation = onNavigateToReadingInterpretation,
          modifier = modifier
        )
      }
    },
  )
}