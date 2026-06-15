package com.taka.runejournal.feature.more.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.settings_reversible_runes_description
import taka_rune_journal.composeapp.generated.resources.settings_reversible_runes_title
import taka_rune_journal.composeapp.generated.resources.settings_title
import taka_rune_journal.composeapp.generated.resources.settings_your_name

@Composable
fun SettingsScreen(
  viewModel: SettingsViewModel,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val uiState by viewModel.uiState.collectAsState()

  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      TakaTopBar(
        title = stringResource(Res.string.settings_title),
        navigationIcon = TakaTopBarNavigationIcon.Back,
        onNavigationClick = onBackClick,
      )
    },
  ) { innerPadding ->
    SettingsContent(
      displayName = uiState.displayName,
      reversedRunesEnabled = uiState.reversedRunesEnabled,
      onDisplayNameChange = viewModel::setDisplayName,
      onReversedRunesEnabledChange = viewModel::setReversedRunesEnabled,
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(24.dp),
    )
  }
}

@Preview
@Composable
private fun SettingsContent(
  displayName: String = "",
  reversedRunesEnabled: Boolean = true,
  onDisplayNameChange: (String) -> Unit = {},
  onReversedRunesEnabledChange: (Boolean) -> Unit = {},
  modifier: Modifier = Modifier,
) {
  val focusManager = LocalFocusManager.current
  var nameInput by rememberSaveable { mutableStateOf(displayName) }

  LaunchedEffect(displayName) {
    nameInput = displayName
  }

  fun onSaveName() {
    val trimmedName = nameInput.trim()
    if (trimmedName != displayName) {
      onDisplayNameChange(trimmedName)
    }
  }

  Column(
    modifier = modifier.pointerInput(Unit) {
      detectTapGestures(
        onTap = {
          focusManager.clearFocus()
        },
      )
    },
    verticalArrangement = Arrangement.spacedBy(24.dp),
  ) {
    OutlinedTextField(
      value = nameInput,
      onValueChange = { nameInput = it },
      modifier = Modifier
        .fillMaxWidth()
        .onFocusChanged { focusState ->
          if (!focusState.isFocused) {
            onSaveName()
          }
        },
      label = { Text(stringResource(Res.string.settings_your_name)) },
      singleLine = true,
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
      ),
      keyboardActions = KeyboardActions(
        onDone = {
          onSaveName()
          focusManager.clearFocus()
        },
      ),
    )

    HorizontalDivider(
      color = MaterialTheme.colorScheme.outlineVariant,
    )

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(4.dp),
      ) {
        Text(
          text = stringResource(Res.string.settings_reversible_runes_title),
          style = MaterialTheme.typography.titleMedium,
        )

        Text(
          text =  stringResource(Res.string.settings_reversible_runes_description),
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }

      Switch(
        checked = reversedRunesEnabled,
        onCheckedChange = onReversedRunesEnabledChange,
        modifier = Modifier.padding(start = 16.dp),
      )
    }
  }
}