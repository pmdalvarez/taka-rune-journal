package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_greeting
import taka_rune_journal.composeapp.generated.resources.timeline_prompts
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_greeting
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_prompt

@Composable
fun GreetingSection(
  displayName: String?,
  dailyPrompt: String?,
  onInitializeDailyPrompt: (List<String>) -> Unit,
) {
  if (displayName.isNullOrBlank()) {
    // If no name given, show welcome greeting + prompt asking for name
    Text(
      text = stringResource(Res.string.timeline_welcome_greeting),
      style = MaterialTheme.typography.headlineMedium
    )
    Text(
      text = stringResource(Res.string.timeline_welcome_prompt),
      modifier = Modifier.padding(top = 8.dp),
      style = MaterialTheme.typography.bodyLarge
    )
  } else {
    // If name is set, show greeting using their name with daily prompt

    if (dailyPrompt == null) {
      // initialise dailyPrompt only if it hasn't been set yet
      val prompts = stringArrayResource(Res.array.timeline_prompts)
      LaunchedEffect(Unit) {
        onInitializeDailyPrompt(prompts)
      }
    }

    Text(
      text = stringResource(Res.string.timeline_greeting, displayName),
      style = MaterialTheme.typography.headlineMedium
    )
    dailyPrompt?.let {
      Text(
        text = it,
        modifier = Modifier.padding(top = 8.dp),
        style = MaterialTheme.typography.bodyLarge
      )
    }
  }

}