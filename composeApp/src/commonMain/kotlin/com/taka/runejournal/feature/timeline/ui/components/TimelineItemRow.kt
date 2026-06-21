package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.feature.timeline.ui.TimelineItemUiModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun TimelineItemRow(
  item: TimelineItemUiModel,
  onTimelineDetailClick: (Long) -> Unit,
  onDeleteClick: (Long, String?, String) -> Unit,
) {
  val itemType = stringResource(item.itemTypeRes)
  Row(
    modifier = Modifier.clickable(
      onClick = { onTimelineDetailClick(item.id) }
    )
  ) {
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
  Row(
    modifier = Modifier.clickable(
      onClick = { onTimelineDetailClick(item.id) }
    )
  ) {
    TakaButton(
      onClick = { onDeleteClick(item.id, item.title, itemType) },
      modifier = Modifier.padding(top = 24.dp)
    ) {
      Text("Delete")
    }
  }


}