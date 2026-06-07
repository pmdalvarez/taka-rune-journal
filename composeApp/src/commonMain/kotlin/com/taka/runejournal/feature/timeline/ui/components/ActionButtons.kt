package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_journal
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_reading

@Composable
fun ActionButtons(
  onNewReadingClick: () -> Unit,
  onNewJournalEntryClick: () -> Unit,
) {

  Button(
    onClick = onNewReadingClick,
    modifier = Modifier.padding(top = 24.dp)
  ) {
    Text(stringResource(Res.string.timeline_button_new_reading))
  }

  Button(
    onClick = onNewJournalEntryClick,
    modifier = Modifier.padding(top = 24.dp)
  ) {
    Text(stringResource(Res.string.timeline_button_new_journal))
  }

}