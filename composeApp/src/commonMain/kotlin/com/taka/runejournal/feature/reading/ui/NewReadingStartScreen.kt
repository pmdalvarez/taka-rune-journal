package com.taka.runejournal.feature.reading.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.RuneSpread
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTextField
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.button_draw_runes
import taka_rune_journal.composeapp.generated.resources.reading_choose_spread
import taka_rune_journal.composeapp.generated.resources.reading_choose_topic
import taka_rune_journal.composeapp.generated.resources.reading_question_description
import taka_rune_journal.composeapp.generated.resources.reading_question_textfield_label
import taka_rune_journal.composeapp.generated.resources.reading_question_title
import taka_rune_journal.composeapp.generated.resources.reading_start_topbar_title

@Composable
fun NewReadingStartScreen(
  viewModel: NewReadingViewModel,
  onBackClick: () -> Unit,
  onContinueClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  val snackbarHostState = remember { SnackbarHostState() }
  var spreadInput by rememberSaveable { mutableStateOf<RuneSpread?>(null) }
  var topicInput by rememberSaveable { mutableStateOf(ReadingTopic.GENERAL) }
  var questionInput by rememberSaveable { mutableStateOf("") }

  LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
      when (event) {
        is UiEvent.ShowError -> { snackbarHostState.showErrorSnackbar(message = getString(event.messageRes)) }
        is UiEvent.NavigateForward -> onContinueClick()
        else -> {} // No other events expected
      }
    }
  }
  TakaScaffold(
    modifier = modifier,
    snackbarHost = { TakaSnackbarHost(hostState = snackbarHostState) },
    topBar = {
      TakaTopBar(
        title = stringResource(Res.string.reading_start_topbar_title),
        navigationIcon = TakaTopBarNavigationIcon.Back,
        onNavigationClick = onBackClick
      )
    }
  ) { contentModifier ->
    Column(
      modifier = contentModifier.fillMaxSize(),
    ) {
      Text(
        text = stringResource(Res.string.reading_choose_spread),
        style = MaterialTheme.typography.headlineMedium
      )
      // Spread Selection
      Text(
        text = stringResource(Res.string.reading_choose_topic),
        style = MaterialTheme.typography.headlineMedium
      )
      // Choose Topic
      Text(
        text = stringResource(Res.string.reading_question_title),
        style = MaterialTheme.typography.headlineMedium
      )
      Text(
        text = stringResource(Res.string.reading_question_description),
        style = MaterialTheme.typography.headlineMedium
      )
      TakaTextField(
        value = questionInput,
        onValueChange = { questionInput = it },
        label = stringResource(Res.string.reading_question_textfield_label),
        singleLine = true,
      )

      TakaButton(
        onClick = {
          viewModel.updateSelections(
            spread = spreadInput!!,
            topic = topicInput,
            question = questionInput
          )
        },
        enabled = spreadInput != null,
      ) {
        Text(stringResource(Res.string.button_draw_runes))
      }
    }
  }

}
