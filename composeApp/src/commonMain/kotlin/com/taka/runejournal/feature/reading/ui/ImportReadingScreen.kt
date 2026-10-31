package com.taka.runejournal.feature.reading.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_import_topbar_title

@Composable
fun ImportReadingScreen(
  viewModel: ImportReadingViewModel,
  onBackClick: () -> Unit,
  onNavigateToReading: (Long) -> Unit,
  modifier: Modifier = Modifier
) {
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
      when (event) {
        is UiEvent.ShowError -> snackbarHostState.showErrorSnackbar(message = getString(event.messageRes))
        is UiEvent.NavigateToItem -> onNavigateToReading(event.itemId)
        else -> {} // No other events expected
      }
    }
  }

  TakaScaffold(
    modifier = modifier,
    snackbarHost = { TakaSnackbarHost(hostState = snackbarHostState) },
    topBar = {
      TakaTopBar(
        title = stringResource(Res.string.reading_import_topbar_title),
        navigationIcon = TakaTopBarNavigationIcon.Back,
        onNavigationClick = onBackClick,
      )
    },
  ) { contentModifier ->
    Text(
      text = "Import Reading Screen",
      modifier = contentModifier
    )
  }
}
