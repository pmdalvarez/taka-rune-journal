package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.ic_new_journal_entry_icon
import taka_rune_journal.composeapp.generated.resources.ic_new_reading_icon
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
      .padding(top = TakaContentSpacing, start = 8.dp, end = 8.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    TakaButton(
      onClick = onNewReadingClick,
      modifier = Modifier
        .weight(1f)
        .padding(end = 8.dp),
    ) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Icon(
          painter = painterResource(Res.drawable.ic_new_reading_icon),
          contentDescription = null,
          modifier = Modifier.size(20.dp),
          tint = MaterialTheme.colorScheme.onPrimary
        )
        Text(stringResource(Res.string.timeline_button_new_reading))
      }
    }

    TakaButton(
      onClick = onNewJournalEntryClick,
      modifier = Modifier
        .weight(1f)
        .padding(end = 8.dp),
    ) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Icon(
          painter = painterResource(Res.drawable.ic_new_journal_entry_icon),
          contentDescription = null,
          modifier = Modifier.size(20.dp),
          tint = MaterialTheme.colorScheme.onPrimary
        )
        Text(stringResource(Res.string.timeline_button_new_journal_entry))
      }
    }
  }
}