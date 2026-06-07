package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.feature.timeline.ui.TimelineItemUiModel

@Composable
fun TimelineItemRow(
  item: TimelineItemUiModel,
  onTimelineDetailClick: (Long) -> Unit,
) {
  Row {
    Text(
      text = item.createdAt.toString(),
      modifier = Modifier.padding(top = 8.dp),
      style = MaterialTheme.typography.bodyLarge
    )
    Text(
      text = item.title?: "no title",
      modifier = Modifier.padding(top = 8.dp),
      style = MaterialTheme.typography.bodyLarge
    )
    Text(
      text = item.notesPreview?: "",
      modifier = Modifier.padding(top = 8.dp),
      style = MaterialTheme.typography.bodyLarge
    )
  }
}