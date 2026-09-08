package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_greeting_afternoon
import taka_rune_journal.composeapp.generated.resources.timeline_greeting_evening
import taka_rune_journal.composeapp.generated.resources.timeline_greeting_morning
import taka_rune_journal.composeapp.generated.resources.timeline_greeting_with_name_afternoon
import taka_rune_journal.composeapp.generated.resources.timeline_greeting_with_name_evening
import taka_rune_journal.composeapp.generated.resources.timeline_greeting_with_name_morning
import taka_rune_journal.composeapp.generated.resources.timeline_prompts
import kotlin.time.Clock

@Composable
fun GreetingSection(
  displayName: String?,
  dailyPrompt: String?,
  onInitializeDailyPrompt: (List<String>) -> Unit,
) {
  if (dailyPrompt == null) {
    // initialise dailyPrompt only if it hasn't been set yet
    val prompts = stringArrayResource(Res.array.timeline_prompts)
    LaunchedEffect(Unit) {
      onInitializeDailyPrompt(prompts)
    }
  }

  Text(
    text = currentTimeGreeting(displayName),
    style = MaterialTheme.typography.headlineMedium,
    textAlign = TextAlign.Center
  )

  dailyPrompt?.let {
    Text(
      text = it,
      modifier = Modifier.padding(top = TakaContentSpacing).fillMaxWidth(),
      style = MaterialTheme.typography.bodyLarge
    )
  }
}


@Composable
private fun currentTimeGreeting(
  name: String? = null
): String {
  val hour = Clock.System.now()
    .toLocalDateTime(TimeZone.currentSystemDefault())
    .time
    .hour
  return if (name.isNullOrBlank()) {
    when (hour) {
      in 5..11 -> stringResource(Res.string.timeline_greeting_morning)
      in 12..18 -> stringResource(Res.string.timeline_greeting_afternoon)
      else -> stringResource(Res.string.timeline_greeting_evening)
    }
  } else {
    when (hour) {
      in 5..11 -> stringResource(Res.string.timeline_greeting_with_name_morning, name)
      in 12..18 -> stringResource(Res.string.timeline_greeting_with_name_afternoon, name)
      else -> stringResource(Res.string.timeline_greeting_with_name_evening, name)
    }
  }
}
