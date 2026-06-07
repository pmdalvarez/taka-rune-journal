package com.taka.runejournal.feature.timeline.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.feature.timeline.ui.components.TimelineItemList
import org.jetbrains.compose.resources.stringArrayResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_prompts

@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel,
    onSettingsClick: () -> Unit,
    onTimelineDetailClick: (Long) -> Unit,
    onNewReadingClick: () -> Unit,
    onNewJournalEntryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val prompts = stringArrayResource(Res.array.timeline_prompts)
    LaunchedEffect(prompts) {
        viewModel.setDailyPrompt(prompts)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TimelineItemList(
            displayName = uiState.displayName,
            prompt = uiState.prompt,
            timelineItems = viewModel.timelineItems,
            onTimelineDetailClick = onTimelineDetailClick,
            onNewReadingClick = onNewReadingClick,
            onNewJournalEntryClick = onNewJournalEntryClick,
            testArea = { testArea(viewModel, onSettingsClick) }
        )

    }
}

@Composable
private fun testArea(viewModel: TimelineViewModel, onSettingsClick: () -> Unit) {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
    Button(
        onClick = onSettingsClick,
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("Open Settings (TODO Move to top bar)")
    }

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