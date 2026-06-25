package com.taka.runejournal.feature.timeline.ui

import DeleteTimelineEntryDialog
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarAction
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.feature.timeline.ui.components.JournalEntryDetail
import com.taka.runejournal.feature.timeline.ui.components.JournalEntryEditor
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.journal_entry_detail_title
import taka_rune_journal.composeapp.generated.resources.timeline_item_title_untitled
import taka_rune_journal.composeapp.generated.resources.timeline_item_type_journal_entry

@Composable
fun JournalEntryDetailScreen(
  viewModel: JournalEntryDetailViewModel,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  val topbarTitle = stringResource(Res.string.journal_entry_detail_title)
  val snackbarHostState = remember { SnackbarHostState() }
  var titleInput by rememberSaveable { mutableStateOf(uiState.title ?: "") }
  var notesInput by rememberSaveable { mutableStateOf(uiState.notes) }

  LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
      when (event) {
        is UiEvent.NavigateBack -> onBackClick()
        is UiEvent.ShowError -> { snackbarHostState.showErrorSnackbar(message = getString(event.messageRes)) }
        else -> {} // No other events expected
      }
    }
  }

  LaunchedEffect(uiState.mode) {
    if (uiState.mode == JournalEntryDetailMode.isEditing) {
      titleInput = uiState.title ?: ""
      notesInput = uiState.notes
    }
  }

  TakaScaffold(
    modifier = modifier,
    snackbarHost = { TakaSnackbarHost(hostState = snackbarHostState) },
    topBar = {
      when (uiState.mode) {
        JournalEntryDetailMode.isEditing ->
          TakaTopBar(
            title = topbarTitle,
            navigationIcon = TakaTopBarNavigationIcon.Close,
            onNavigationClick = { viewModel.setMode(JournalEntryDetailMode.isViewing)},
            action = TakaTopBarAction.Save(onClick = { viewModel.updateJournalEntry(notesInput, titleInput) })
          )
        JournalEntryDetailMode.isSaving ->
          TakaTopBar(
            title = topbarTitle,
            navigationIcon = TakaTopBarNavigationIcon.Close,
            onNavigationClick = { viewModel.setMode(JournalEntryDetailMode.isViewing)},
            action = TakaTopBarAction.Save(enabled = false)
          )
        else ->
          TakaTopBar(
            title = topbarTitle,
            navigationIcon = TakaTopBarNavigationIcon.Back,
            onNavigationClick = onBackClick,
            action = TakaTopBarAction.JournalEntryDetailActions(
              onEditClick = { viewModel.setMode(JournalEntryDetailMode.isEditing) },
              onDeleteClick = { viewModel.openDeleteDialog() }
            )
          )
      }
    }
  ) { contentModifier ->
    when (uiState.mode) {
      JournalEntryDetailMode.isEditing, JournalEntryDetailMode.isSaving ->
        JournalEntryEditor(
          modifier = contentModifier,
          titleValue = titleInput,
          titleOnValueChange = { titleInput = it },
          notesValue = notesInput,
          notesOnValueChange = { notesInput = it }
        )
      else ->
        JournalEntryDetail(
          modifier = contentModifier,
          title = if (!uiState.title.isNullOrBlank()) uiState.title!! else stringResource(Res.string.timeline_item_title_untitled),
          createdAt = uiState.createdAt,
          notes = uiState.notes
        )
    }
  }

  if (uiState.mode == JournalEntryDetailMode.isDeleting) {
    DeleteTimelineEntryDialog(
      onDismiss = viewModel::dismissDeleteDialog,
      onConfirm = viewModel::deleteJournalEntry,
      stringResource(Res.string.timeline_item_type_journal_entry),
      null
    )
  }
}

