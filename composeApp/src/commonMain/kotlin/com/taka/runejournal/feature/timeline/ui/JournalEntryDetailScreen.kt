package com.taka.runejournal.feature.timeline.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarAction
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.core.ui.components.showInfoSnackbar
import com.taka.runejournal.feature.timeline.ui.components.JournalEntryDetail
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.journal_entry_detail_title

@Composable
fun JournalEntryDetailScreen(
  viewModel: JournalEntryDetailViewModel,
  timelineItemId: Long?,
  onBackClick: () -> Unit,
  onSaved: () -> Unit,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  val topbarTitle = stringResource(Res.string.journal_entry_detail_title)
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
      when (event) {
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
          onDeleteClick = {}
        )
      )
    }
  ) { contentModifier ->
    Column(
      modifier = contentModifier,
      verticalArrangement = Arrangement.spacedBy(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      JournalEntryDetail(
        title = uiState.title,
        createdAt = uiState.createdAt,
        notes = uiState.notes
      )
    }
  }
}