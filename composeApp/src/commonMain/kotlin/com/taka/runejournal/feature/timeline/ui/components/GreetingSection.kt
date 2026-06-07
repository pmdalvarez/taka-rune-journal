package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_greeting_with_name
import taka_rune_journal.composeapp.generated.resources.timeline_greeting_without_name
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_message

@Composable
fun GreetingSection(
  displayName: String?,
  prompt: String?,
) {
  val greeting = if (displayName.isNullOrBlank()) {
    stringResource(Res.string.timeline_greeting_without_name)
  } else {
    stringResource(Res.string.timeline_greeting_with_name, displayName)
  }
  Text(
    text = greeting,
    style = MaterialTheme.typography.headlineMedium
  )

  val postGreetingText = if (displayName.isNullOrBlank()) {
    stringResource(Res.string.timeline_welcome_message)
  } else {
    prompt
  }
  postGreetingText?.let { postGreetingText ->
    Text(
      text = postGreetingText,
      modifier = Modifier.padding(top = 8.dp),
      style = MaterialTheme.typography.bodyLarge
    )
  }

}