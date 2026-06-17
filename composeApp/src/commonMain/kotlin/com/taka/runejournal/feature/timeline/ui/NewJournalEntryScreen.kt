package com.taka.runejournal.feature.timeline.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.new_journal_entry_title

@Composable
fun NewJournalEntryScreen(
  viewModel: NewJournalEntryViewModel,
  onBackClick: () -> Unit,
  onSaved: () -> Unit,
  modifier: Modifier = Modifier
) {
  TakaScaffold(
    modifier = modifier,
    topBar = {
      TakaTopBar(
        title = stringResource(Res.string.new_journal_entry_title),
        navigationIcon = TakaTopBarNavigationIcon.Close,
        onNavigationClick = onBackClick,
      )
    },
  ) { contentModifier ->
    Column(
      modifier = contentModifier,
      verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
      Text(
        text = "New Journal Entry page",
        style =  MaterialTheme.typography.bodyLarge,
      )
    }
  }
}