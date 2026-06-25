package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun JournalEntryDetail(
  modifier: Modifier,
  title: String,
  formattedDate: String,
  notes: String
) {
  Column(
    modifier = modifier.fillMaxSize()
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.headlineSmall,
      color = MaterialTheme.colorScheme.onBackground
    )
    Text(
      text = formattedDate,
      modifier = Modifier.padding(top = 4.dp),
      style = MaterialTheme.typography.labelMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Surface(
      shape = MaterialTheme.shapes.small,
      color = MaterialTheme.colorScheme.surfaceContainer,
      contentColor = MaterialTheme.colorScheme.onSurface,
      border = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant,
      ),
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 24.dp)
    ) {
      Text(
        text = notes,
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
  }
}