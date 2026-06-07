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
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_journal
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_reading
import taka_rune_journal.composeapp.generated.resources.timeline_greeting_with_name
import taka_rune_journal.composeapp.generated.resources.timeline_greeting_without_name
import taka_rune_journal.composeapp.generated.resources.timeline_prompts
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_message

@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel,
    onSettingsClick: () -> Unit,
    onTimelineDetailClick: (Long) -> Unit,
    onNewJournalEntryClick: () -> Unit,
    onNewReadingClick: () -> Unit,
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
        val greeting = if (uiState.displayName.isNullOrBlank()) {
            stringResource(Res.string.timeline_greeting_without_name)
        } else {
            stringResource(Res.string.timeline_greeting_with_name, uiState.displayName!!)
        }
        Text(
            text = greeting,
            style = MaterialTheme.typography.headlineMedium
        )

        val postGreetingText = if (uiState.displayName.isNullOrBlank()) {
            stringResource(Res.string.timeline_welcome_message)
        } else {
            uiState.prompt
        }
        postGreetingText?.let { postGreetingText ->
            Text(
                text = postGreetingText,
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = onSettingsClick,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("Open Settings")
        }

        Button(
            onClick = onNewReadingClick,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(stringResource(Res.string.timeline_button_new_reading))
        }

        Button(
            onClick = onNewJournalEntryClick,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(stringResource(Res.string.timeline_button_new_journal))
        }

        testArea(viewModel)

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