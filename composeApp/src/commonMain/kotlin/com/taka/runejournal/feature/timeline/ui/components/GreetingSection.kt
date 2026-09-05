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
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.StringResource
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
  onDisplayNameEntered: (String) -> Unit
) {
  // If logic ensures that after user enters a name, the text field changed to a greeting
//  if (displayName.isNullOrEmpty()) {
//    DisplayNameTextField(
//      onSaveName = {
//        if (it.isNotBlank()) {
//          onDisplayNameEntered(it)
//        }
//      },
//      modifier = Modifier.padding(top = TakaContentSpacing)
//    )
//  }
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

//@Composable
//fun DisplayNameTextField(
//    onSaveName: (String) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//  val focusManager = LocalFocusManager.current
//  var nameInput by rememberSaveable { mutableStateOf("") }
//
//  TakaTextField(
//      value = nameInput,
//      onValueChange = { nameInput = it },
//      label = stringResource(Res.string.timeline_textfield_label_your_name),
//      singleLine = true,
//      keyboardOptions = KeyboardOptions(
//          imeAction = ImeAction.Done,
//      ),
//      keyboardActions = KeyboardActions(
//          onDone = {
//            focusManager.clearFocus() // trigger the onFocusChanged lambda
//          },
//      ),
//      modifier = modifier
//        .onFocusChanged() { focusState ->
//          if (!focusState.isFocused) {
//            onSaveName(nameInput)
//          }
//      }
//  )
//}

@Composable
private fun currentTimeGreeting(
  name: String? = null
): String = when (
  Clock.System.now()
  .toLocalDateTime(TimeZone.currentSystemDefault())
  .time
  .hour
) {
    in 5..11 -> name?.let {
      stringResource(Res.string.timeline_greeting_with_name_morning, name)
    } ?: stringResource(Res.string.timeline_greeting_morning)
    in 12..18 -> name?.let {
      stringResource(Res.string.timeline_greeting_with_name_afternoon, name)
    } ?: stringResource(Res.string.timeline_greeting_afternoon)
    else -> name?.let {
      stringResource(Res.string.timeline_greeting_with_name_evening, name)
    } ?: stringResource(Res.string.timeline_greeting_evening)
}
