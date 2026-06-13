package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.TakaButton
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_journal
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_reading

@Composable
fun ActionButtons(
  onNewReadingClick: () -> Unit = {},
  onNewJournalEntryClick: () -> Unit = {},
) {

  TakaButton(
    onClick = onNewReadingClick,
    modifier = Modifier.padding(top = 24.dp)
  ) {
    Text(stringResource(Res.string.timeline_button_new_reading))
  }

  TakaButton(
    onClick = onNewJournalEntryClick,
    modifier = Modifier.padding(top = 24.dp)
  ) {
    Text(stringResource(Res.string.timeline_button_new_journal))
  }

}