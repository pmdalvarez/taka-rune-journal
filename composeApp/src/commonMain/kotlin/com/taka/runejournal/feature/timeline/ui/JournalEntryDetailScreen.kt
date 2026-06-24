package com.taka.runejournal.feature.timeline.ui

import DeleteTimelineEntryDialog
import androidx.compose.material3.SnackbarHostState
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
import com.taka.runejournal.feature.timeline.ui.components.JournalEntryDetail
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.journal_entry_detail_title
import taka_rune_journal.composeapp.generated.resources.timeline_item_title_untitled

@Composable
fun JournalEntryDetailScreen(
  viewModel: JournalEntryDetailViewModel,
  onBackClick: () -> Unit,
  onSaved: () -> Unit,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  val topbarTitle = stringResource(Res.string.journal_entry_detail_title)
  val snackbarHostState = remember { SnackbarHostState() }
  val journalEntryTitle = if (!uiState.title.isNullOrBlank()) uiState.title else stringResource(Res.string.timeline_item_title_untitled)

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
        title = topbarTitle,
        navigationIcon = TakaTopBarNavigationIcon.Back,
        onNavigationClick = onBackClick,
        action = TakaTopBarAction.TimelineDetailActions(
          onEditClick = {},
          onDeleteClick = { viewModel.openDeleteDialog() }
        )
      )
    }
  ) { contentModifier ->
    JournalEntryDetail(
      modifier = contentModifier,
      title = journalEntryTitle!!,
      createdAt = uiState.createdAt,
      notes = uiState.notes
    )
  }

  if (uiState.showDeleteDialog) {
    DeleteTimelineEntryDialog(
      onDismiss = viewModel::dismissDeleteDialog,
      onConfirm = viewModel::deleteJournalEntry,
      journalEntryTitle!!,
      uiState.notes
    )
  }
}