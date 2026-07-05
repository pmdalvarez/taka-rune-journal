package com.taka.runejournal.feature.reading.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.RuneSpread
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSelectableCard
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTextField
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.core.ui.theme.TakaCardHorizontalSpacing
import com.taka.runejournal.core.ui.theme.TakaCardPadding
import com.taka.runejournal.core.ui.theme.TakaCardSpacing
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceSm
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
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
      modifier = contentModifier.fillMaxSize()
      .verticalScroll(rememberScrollState()),
    ) {
      Text(
        text = stringResource(Res.string.reading_choose_spread),
        style = MaterialTheme.typography.titleMedium
      )
      Row(
        modifier = Modifier
          .padding(top = TakaContentSpacing)
          .fillMaxWidth()
          .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(TakaCardHorizontalSpacing)
      ) {
        RuneSpread.entries.forEach { spread ->
          TakaSelectableCard(
            onClick = { spreadInput = spread },
            isSelected = spread == spreadInput,
            modifier = Modifier
              .weight(1f),
            ) {
            Icon(
              painter = painterResource(spread.icon),
              contentDescription = stringResource(spread.title),
              modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(72.dp),
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
              modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = TakaCardPadding),
              text = stringResource(spread.title),
              style = MaterialTheme.typography.titleSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
              modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = TakaCardPadding),
              text = stringResource(spread.description),
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }
      Text(
        text = stringResource(Res.string.reading_choose_topic),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = TakaSectionSpacing)
      )
      Row(
        modifier = Modifier
          .padding(top = TakaContentSpacing)
          .fillMaxWidth()
          .height(IntrinsicSize.Max)
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(TakaCardHorizontalSpacing)
      ) {
        ReadingTopic.entries.forEach { topic ->
          TakaSelectableCard(
            onClick = { topicInput = topic },
            isSelected = topic == topicInput,
            modifier = Modifier
              .width(220.dp), // TODO base this on 60% of screen width
          ) {
            Icon(
              painter = painterResource(topic.icon),
              contentDescription = stringResource(topic.title),
              modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(72.dp),
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
              modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = TakaCardPadding),
              text = stringResource(topic.title),
              style = MaterialTheme.typography.titleSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
              modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = TakaCardPadding),
              text = stringResource(topic.description),
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }
      Text(
        text = stringResource(Res.string.reading_question_description),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(top = TakaContentSpacing)
      )
      Text(
        text = stringResource(Res.string.reading_question_title),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = TakaSectionSpacing)
      )
      Text(
        text = stringResource(Res.string.reading_question_description),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(top = TakaContentSpacing)
      )
      TakaTextField(
        value = questionInput,
        onValueChange = { questionInput = it },
        label = stringResource(Res.string.reading_question_textfield_label),
        singleLine = true,
        modifier = Modifier.padding(top = TakaContentSpacing)
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
        modifier = Modifier
          .padding(top = TakaContentSpacing)
          .align(Alignment.CenterHorizontally)
      ) {
        Text(stringResource(Res.string.button_draw_runes))
      }
    }
  }

}
