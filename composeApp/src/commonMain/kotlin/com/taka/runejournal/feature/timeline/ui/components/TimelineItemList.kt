package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.taka.runejournal.feature.timeline.ui.TimelineItemUiModel
import kotlinx.coroutines.flow.Flow

@Composable
fun TimelineItemList(
  displayName: String?,
  prompt: String?,
  timelineItems: Flow<PagingData<TimelineItemUiModel>>,
  onTimelineDetailClick: (Long) -> Unit,
  onNewReadingClick: () -> Unit,
  onNewJournalEntryClick: () -> Unit,
  testArea: @Composable () -> Unit
) {
  val pagingItems = timelineItems.collectAsLazyPagingItems()
  LazyColumn (modifier = Modifier.padding(top = 24.dp)) {
    item { GreetingSection(displayName, prompt) }
    items(
      count = pagingItems.itemCount,
      key = pagingItems.itemKey { it.id }
    ) { index ->
      val item = pagingItems[index]
      item?.let {
        TimelineItemRow(
          it,
          onTimelineDetailClick
        )
      }
    }
    item { ActionButtons(onNewReadingClick, onNewJournalEntryClick) }
    item { testArea() }
  }
}