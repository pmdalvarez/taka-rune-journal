package com.taka.runejournal.feature.timeline.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarAction
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.new_journal_entry_textfield_label_notes
import taka_rune_journal.composeapp.generated.resources.new_journal_entry_textfield_label_title
import taka_rune_journal.composeapp.generated.resources.new_journal_entry_title

@Composable
fun NewJournalEntryScreen(
  viewModel: NewJournalEntryViewModel,
  onBackClick: () -> Unit,
  onSaved: () -> Unit,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  var titleInput by rememberSaveable { mutableStateOf("") }
  var notesInput by rememberSaveable { mutableStateOf("") }
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
      when (event) {
        is NewJournalEntryUiEvent.NavigateBack -> onBackClick()
        is NewJournalEntryUiEvent.ShowError -> {
          snackbarHostState.showSnackbar(
            message = getString(event.messageRes),
          )
        }
      }
    }
  }

  TakaScaffold(
    modifier = modifier,
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState)
    },
    topBar = {
      TakaTopBar(
        title = stringResource(Res.string.new_journal_entry_title),
        navigationIcon = TakaTopBarNavigationIcon.Close,
        onNavigationClick = onBackClick,
        action = if (uiState.isSaving) {
          TakaTopBarAction.Saving
        } else {
          TakaTopBarAction.Save(
            onClick = {
              viewModel.createJournalEntry(notesInput, titleInput)
            }
          )
        }
      )
    },
  ) { contentModifier ->
    Column(
      modifier = contentModifier,
      verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
      OutlinedTextField(
        value = titleInput,
        onValueChange = { titleInput = it },
        label = { Text(stringResource(Res.string.new_journal_entry_textfield_label_title)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
      )
      OutlinedTextField(
        value = notesInput,
        onValueChange = { notesInput = it },
        label = { Text(stringResource(Res.string.new_journal_entry_textfield_label_notes)) },
        minLines = 5,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}