package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.TakaButton
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_journal_entry
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_reading

@Composable
fun ActionButtons(
  onNewReadingClick: () -> Unit = {},
  onNewJournalEntryClick: () -> Unit = {},
) {
  Row (
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 24.dp, start = 8.dp, end = 8.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    TakaButton(
      onClick = onNewReadingClick,
      modifier = Modifier
        .weight(1f)
        .padding(end = 8.dp),
    ) {
      Text(stringResource(Res.string.timeline_button_new_reading))
    }

    TakaButton(
      onClick = onNewJournalEntryClick,
      modifier = Modifier
        .weight(1f)
        .padding(end = 8.dp),
    ) {
      Text(stringResource(Res.string.timeline_button_new_journal_entry))
    }
  }
}