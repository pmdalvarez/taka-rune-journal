package com.taka.runejournal.feature.timeline.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_greeting_with_name
import taka_rune_journal.composeapp.generated.resources.timeline_greeting_without_name
import taka_rune_journal.composeapp.generated.resources.timeline_prompt1

@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.uiState.value.displayName.isNullOrBlank()) {
            Text(
                text = stringResource(Res.string.timeline_greeting_without_name),
                style = MaterialTheme.typography.headlineMedium
            )
        } else {
            Text(
                text = stringResource(Res.string.timeline_greeting_with_name, viewModel.uiState.value.displayName!!),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Text(
            text = stringResource(Res.string.timeline_prompt1),
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onAboutClick,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("Open About")
        }
    }
}