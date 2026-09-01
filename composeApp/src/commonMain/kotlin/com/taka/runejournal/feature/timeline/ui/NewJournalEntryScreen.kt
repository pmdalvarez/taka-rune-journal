package com.taka.runejournal.feature.timeline.ui

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarAction
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.feature.timeline.ui.components.JournalEntryEditor
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.new_journal_entry_title

@Composable
fun NewJournalEntryScreen(
  viewModel: NewJournalEntryViewModel,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  var titleInput by rememberSaveable { mutableStateOf("") }
  var notesInput by rememberSaveable { mutableStateOf("") }
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
        title = stringResource(Res.string.new_journal_entry_title),
        navigationIcon = TakaTopBarNavigationIcon.Close,
        onNavigationClick = onBackClick,
        action = if (uiState.isSaving) {
          TakaTopBarAction.Save(enabled = false)
        } else {
          TakaTopBarAction.Save(
            onClick = { viewModel.createJournalEntry(notesInput, titleInput) }
          )
        }
      )
    },
  ) { contentModifier ->
    JournalEntryEditor(
      modifier = contentModifier,
      titleValue = titleInput,
      titleOnValueChange = { titleInput = it },
      notesValue = notesInput,
      notesOnValueChange = { notesInput = it }
    )
  }
}