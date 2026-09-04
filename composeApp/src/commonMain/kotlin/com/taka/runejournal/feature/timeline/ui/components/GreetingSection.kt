package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.taka.runejournal.core.ui.components.TakaTextField
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_greeting
import taka_rune_journal.composeapp.generated.resources.timeline_prompts
import taka_rune_journal.composeapp.generated.resources.timeline_textfield_label_your_name
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_back

@Composable
fun GreetingSection(
  displayName: String?,
  dailyPrompt: String?,
  onInitializeDailyPrompt: (List<String>) -> Unit,
  onDisplayNameEntered: (String) -> Unit
) {
  // If logic ensures that after user enters a name, the text field changed to a greeting
  if (displayName.isNullOrEmpty()) {
    DisplayNameTextField(
      onSaveName = {
        if (it.isNotBlank()) {
          onDisplayNameEntered(it)
        }
      },
      modifier = Modifier.padding(top = TakaContentSpacing)
    )
  }
  // If name is set, show greeting using their name with daily prompt

  if (dailyPrompt == null) {
    // initialise dailyPrompt only if it hasn't been set yet
    val prompts = stringArrayResource(Res.array.timeline_prompts)
    LaunchedEffect(Unit) {
      onInitializeDailyPrompt(prompts)
    }
  }

  val greeting = if (displayName.isNullOrEmpty()) {
    stringResource(Res.string.timeline_welcome_back)
  } else {
    stringResource(Res.string.timeline_greeting, displayName)
  }
  Text(
    text = greeting,
    style = MaterialTheme.typography.headlineMedium
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
fun DisplayNameTextField(
    onSaveName: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
  val focusManager = LocalFocusManager.current
  var nameInput by rememberSaveable { mutableStateOf("") }

  TakaTextField(
      value = nameInput,
      onValueChange = { nameInput = it },
      label = stringResource(Res.string.timeline_textfield_label_your_name),
      singleLine = true,
      keyboardOptions = KeyboardOptions(
          imeAction = ImeAction.Done,
      ),
      keyboardActions = KeyboardActions(
          onDone = {
            focusManager.clearFocus() // trigger the onFocusChanged lambda
          },
      ),
      modifier = modifier
        .onFocusChanged() { focusState ->
          if (!focusState.isFocused) {
            onSaveName(nameInput)
          }
      }
  )
}
