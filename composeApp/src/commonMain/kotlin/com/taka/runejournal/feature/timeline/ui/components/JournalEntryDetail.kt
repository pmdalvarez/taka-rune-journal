package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.utils.format
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_item_title_untitled
import kotlin.time.Instant

@Composable
fun JournalEntryDetail(
  modifier: Modifier,
  title: String?,
  createdAt: Instant,
  notes: String
) {
  Column(
    modifier = modifier.fillMaxSize()
  ) {
    Text(
      text = if (!title.isNullOrBlank()) title else  stringResource(Res.string.timeline_item_title_untitled),
      modifier = Modifier.padding(top = 24.dp),
      style = MaterialTheme.typography.headlineSmall,
      color = MaterialTheme.colorScheme.onBackground
    )
    Text(
      text = createdAt.format(),
      modifier = Modifier.padding(top = 4.dp),
      style = MaterialTheme.typography.labelMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Text(
      text = notes,
      modifier = Modifier.padding(top = 24.dp),
      style = MaterialTheme.typography.bodyLarge,
      color = MaterialTheme.colorScheme.onBackground
    )
  }
}