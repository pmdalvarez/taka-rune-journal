package com.taka.runejournal.feature.more.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTextField
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceLg
import com.taka.runejournal.core.ui.theme.TakaSpaceXs
import org.jetbrains.compose.resources.getString
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
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }
  var nameInput by rememberSaveable { mutableStateOf(uiState.displayName) }

  LaunchedEffect(uiState.displayName) {
    nameInput = uiState.displayName
  }

  fun onSaveName() {
    if (nameInput != uiState.displayName) {
      viewModel.setDisplayName(nameInput)
    }
  }

  fun onNavigateBackWithSave() {
    onSaveName()
    onBackClick()
  }

  NavigationBackHandler(state = rememberNavigationEventState(NavigationEventInfo.None), onBackCompleted = ::onNavigateBackWithSave)

  LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
      when (event) {
        is UiEvent.ShowError -> { snackbarHostState.showErrorSnackbar(message = getString(event.messageRes)) }
        else -> {} // No other events expected
      }
    }
  }

  TakaScaffold(
    modifier = modifier,
    snackbarHost = { TakaSnackbarHost(hostState = snackbarHostState) },
    topBar = {
      TakaTopBar(
        title = stringResource(Res.string.settings_title),
        navigationIcon = TakaTopBarNavigationIcon.Back,
        onNavigationClick = ::onNavigateBackWithSave
      )
    },
  ) { contentModifier ->
    SettingsContent(
      displayName = nameInput,
      reversedRunesEnabled = uiState.reversedRunesEnabled,
      onDisplayNameChange = { nameInput = it },
      onSaveName = ::onSaveName,
      onReversedRunesEnabledChange = viewModel::setReversedRunesEnabled,
      modifier = contentModifier
    )
  }
}

@Preview
@Composable
private fun SettingsContent(
  displayName: String = "",
  reversedRunesEnabled: Boolean = true,
  onDisplayNameChange: (String) -> Unit = {},
  onSaveName: () -> Unit = {},
  onReversedRunesEnabledChange: (Boolean) -> Unit = {},
  modifier: Modifier = Modifier,
) {
  val focusManager = LocalFocusManager.current

  Column(
    modifier = modifier.pointerInput(Unit) {
      detectTapGestures(
        onTap = {
          focusManager.clearFocus()
        },
      )
    },
    verticalArrangement = Arrangement.spacedBy(TakaSectionSpacing),
  ) {
    TakaTextField(
      value = displayName,
      onValueChange = onDisplayNameChange,
      modifier = Modifier
        .onFocusChanged { focusState ->
          if (!focusState.isFocused) {
            onSaveName()
          }
        },
      label = stringResource(Res.string.settings_your_name),
      singleLine = true,
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
      ),
      keyboardActions = KeyboardActions(
        onDone = {
          focusManager.clearFocus() // trigger the onFocusChanged lambda
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
        verticalArrangement = Arrangement.spacedBy(TakaSpaceXs),
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
        modifier = Modifier.padding(start = TakaSpaceLg),
      )
    }
  }
}