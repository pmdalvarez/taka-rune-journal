package com.taka.runejournal.feature.timeline.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.feature.timeline.ui.components.ActionButtons
import com.taka.runejournal.feature.timeline.ui.components.GreetingSection
import com.taka.runejournal.feature.timeline.ui.components.TimelineItemRow

@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel,
    onAboutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onDesignPlaygroundClick: () -> Unit,
    onTimelineDetailClick: (Long) -> Unit,
    onNewReadingClick: () -> Unit,
    onNewJournalEntryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagingItems = viewModel.timelineItems.collectAsLazyPagingItems()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TakaTopBar(
                showMoreMenu = true,
                onSettingsClick = onSettingsClick,
                onAboutClick = onAboutClick,
                onDesignPlaygroundClick = onDesignPlaygroundClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                GreetingSection(
                    uiState.displayName,
                    uiState.dailyPrompt,
                    viewModel::initializeDailyPrompt,
                    viewModel::setDisplayName
                )
            }
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { it.id }
            ) { index ->
                val item = pagingItems[index]
                item?.let {
                    TimelineItemRow(
                        it,
                        onTimelineDetailClick,
                        viewModel::deleteTimelineItem
                    )
                }
            }
            item { ActionButtons(onNewReadingClick, onNewJournalEntryClick) }
            item { testArea(viewModel) }
        }
    }
}

@Composable
private fun testArea(viewModel: TimelineViewModel) {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )

    Button(
        onClick = { viewModel.setDisplayName("Paolo" + (0..100).random()) },
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("Change name to Paolo + random number")
    }

    Button(
        onClick = { viewModel.setDisplayName("") },
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("Change name to empty")
    }

    Button(
        onClick = { viewModel.createJournalEntry("This is a random journal entry with a random number: " + (0..100).random() , "Title" + (0..100).random()) },
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("Add random journal entry")
    }

}