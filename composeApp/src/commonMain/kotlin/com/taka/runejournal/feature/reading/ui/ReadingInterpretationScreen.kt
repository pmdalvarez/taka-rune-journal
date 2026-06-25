package com.taka.runejournal.feature.reading.ui

import DeleteTimelineEntryDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarAction
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_interpretation_delete_dialog_title

@Composable
fun ReadingInterpretationScreen(
  viewModel: ReadingInterpretationViewModel,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
      when (event) {
        is UiEvent.NavigateBack -> onBackClick()
        is UiEvent.ShowError -> { snackbarHostState.showErrorSnackbar(message = getString(event.messageRes)) }
        else -> {} // No other events expected
      }
    }
  }

  TakaScaffold(
    modifier = modifier,
    snackbarHost = { TakaSnackbarHost(hostState = snackbarHostState) },
    topBar = {
          TakaTopBar(
            navigationIcon = TakaTopBarNavigationIcon.Back,
            onNavigationClick = onBackClick,
            action = TakaTopBarAction.RuneInterpretationActions(
              onDeleteClick = { viewModel.openDeleteDialog() }
            )
          )
      }
  ) { contentModifier ->
    Column(
      modifier = modifier.fillMaxSize()
    ) {
      Text(
        text = "Reading interpretation screen",
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onBackground
      )
    }
  }

  if (uiState.showDeleteDialog) {
    DeleteTimelineEntryDialog(
      onDismiss = viewModel::dismissDeleteDialog,
      onConfirm = viewModel::deleteReading,
      stringResource(Res.string.reading_interpretation_delete_dialog_title),
      null
    )
  }
}