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
import kotlin.time.Instant

@Composable
fun JournalEntryDetail(
  modifier: Modifier,
  title: String,
  createdAt: Instant,
  notes: String
) {
  Column(
    modifier = modifier.fillMaxSize()
  ) {
    Text(
      text = title,
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