package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.components.TakaCard
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceXs

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
      modifier = Modifier.padding(top = TakaSpaceXs),
      style = MaterialTheme.typography.labelMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    TakaCard(
      modifier = Modifier.padding(top = TakaSectionSpacing)
    ) {
      Text(
        text = notes,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
  }
}