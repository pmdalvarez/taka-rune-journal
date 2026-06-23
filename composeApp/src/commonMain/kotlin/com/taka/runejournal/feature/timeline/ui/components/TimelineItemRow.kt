package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.utils.format
import com.taka.runejournal.feature.timeline.ui.TimelineItemUiModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.button_delete

@Composable
fun TimelineItemRow(
  item: TimelineItemUiModel,
  onTimelineDetailClick: (Long) -> Unit,
  onDeleteClick: (Long, String?, String) -> Unit,
) {

  val itemType = stringResource(item.itemTypeRes)
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = { onTimelineDetailClick(item.id) }),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      painter = painterResource(item.icon),
      contentDescription = itemType,
      modifier = Modifier.padding(start = 8.dp)
    )
    Text(
      text = item.title?: stringResource(item.itemTypeRes),
      modifier = Modifier.padding(top = 8.dp),
      style = MaterialTheme.typography.bodyLarge
    )
    Text(
      text = item.createdAt.format(),
      modifier = Modifier.padding(top = 8.dp),
      style = MaterialTheme.typography.bodyLarge
    )
    Text(
      text = item.notesPreview?: "",
      modifier = Modifier.padding(top = 8.dp),
      style = MaterialTheme.typography.bodyLarge
    )
    IconButton(
      onClick = { onDeleteClick(item.id, item.title, itemType) },
      modifier = Modifier.padding(top = 8.dp),
    ) {
      Icon(
        imageVector = Icons.Default.Delete,
        contentDescription = stringResource(Res.string.button_delete),
      )
    }
  }


}