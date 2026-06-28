package com.taka.runejournal.feature.reading.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.taka.runejournal.core.navigation.appNavSavedStateConfiguration
import com.taka.runejournal.feature.reading.ui.ReadingDrawScreen
import com.taka.runejournal.feature.reading.ui.ReadingStartScreen
import com.taka.runejournal.feature.reading.ui.ReadingViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ReadingFlowNavDisplay(
  onExitReadingFlow: () -> Unit,
  onReadingSaved: (id: Long) -> Unit,
  modifier: Modifier = Modifier
) {
  val readingBackStack = rememberNavBackStack(appNavSavedStateConfiguration, ReadingStartRoute)

  val viewModel = koinViewModel<ReadingViewModel>()

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
      entry<ReadingStartRoute> {
        ReadingStartScreen(
          viewModel = viewModel,
          onBackClick = {
            onExitReadingFlow()
          },
          onContinueClick = {
            readingBackStack.add(ReadingDrawRoute)
          },
          modifier = modifier
        )
      }

      entry<ReadingDrawRoute> {
        ReadingDrawScreen(
          viewModel = viewModel,
          onBackClick = {
            readingBackStack.removeLastOrNull()
          },
          onReadingSaved = onReadingSaved,
          modifier = modifier
        )
      }
    },
  )
}