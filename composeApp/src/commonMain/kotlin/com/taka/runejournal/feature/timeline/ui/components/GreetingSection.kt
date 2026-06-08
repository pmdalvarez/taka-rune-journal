package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_greeting
import taka_rune_journal.composeapp.generated.resources.timeline_prompts
import taka_rune_journal.composeapp.generated.resources.timeline_textfield_label_your_name
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_greeting
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_prompt

@Composable
fun GreetingSection(
  displayName: String?,
  dailyPrompt: String?,
  onInitializeDailyPrompt: (List<String>) -> Unit,
  onDisplayNameEntered: (String) -> Unit
) {

  // show Welcome screen there is no display name
  // OR if display name but was only just saved (to stay in welcome screen)
  var displayNameEntered by rememberSaveable { mutableStateOf(false) }
  val showWelcomeScreen = displayName.isNullOrEmpty() || displayNameEntered

  if (showWelcomeScreen) {
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
    DisplayNameTextField(
      onSaveName = {
        val displayName = it.trim()
        if (displayName.isNotEmpty()) {
          onDisplayNameEntered(displayName)
          displayNameEntered = true
        }
      },
      modifier = Modifier.padding(top = 24.dp)
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

@Composable
fun DisplayNameTextField(
    onSaveName: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
  var nameInput by rememberSaveable { mutableStateOf("") }

  OutlinedTextField(
      value = nameInput,
      onValueChange = { nameInput = it },
      label = {
          Text(stringResource(Res.string.timeline_textfield_label_your_name))
      },
      singleLine = true,
      keyboardOptions = KeyboardOptions(
          imeAction = ImeAction.Done,
      ),
      keyboardActions = KeyboardActions(
          onDone = {
            onSaveName(nameInput)
          },
      ),
      modifier = modifier.onFocusChanged() { focusState ->
          if (!focusState.isFocused) {
            onSaveName(nameInput)
          }
      }
  )
}
