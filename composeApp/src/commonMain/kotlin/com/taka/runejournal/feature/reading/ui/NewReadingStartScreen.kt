package com.taka.runejournal.feature.reading.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.RuneSpread
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.FadingScrollColumn
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaPagerIndicator
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSelectableCard
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTextField
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.core.ui.theme.TakaCardSpacing
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaScreenPadding
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceMd
import kotlinx.coroutines.launch
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
  onCancelReading: () -> Unit,
  onContinueClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val snackbarHostState = remember { SnackbarHostState() }
  var spreadInput by rememberSaveable { mutableStateOf<RuneSpread?>(null) }
  var topicInput by rememberSaveable { mutableStateOf<ReadingTopic?>(null) }

  var maxUnlockedPage by remember { mutableIntStateOf(0) }
  var requestedPage by remember { mutableStateOf<Int?>(null) }
  val pagerState = rememberPagerState (
    initialPage = 0,
    pageCount = { maxUnlockedPage + 1 } // We can't go forward until we selected spread/topic to unlock next page
  )
  LaunchedEffect(requestedPage) {
    requestedPage?.let {
      // Scrolling done here to avoid race conditions
      if (it <= maxUnlockedPage) {
        pagerState.animateScrollToPage(it)
        requestedPage = null
      }
    }
  }
  val coroutineScope = rememberCoroutineScope()
  val handleBack: () -> Unit = {
    if (pagerState.currentPage > 0) {
      coroutineScope.launch {
        pagerState.animateScrollToPage(
          pagerState.currentPage - 1
        )
      }
    } else {
      onCancelReading()
    }
  }

  NavigationBackHandler(state = rememberNavigationEventState(NavigationEventInfo.None), onBackCompleted = handleBack)

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
        onNavigationClick = handleBack
      )
    }
  ) { contentModifier ->
    Column (
      modifier = contentModifier
        .fillMaxSize()
        .imePadding(), // ensures keyboard doesn't hide enter question field
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Top,
    ) {
      HorizontalPager(
        modifier = modifier.weight(1f),
        state = pagerState,
      ) { page ->
        when (page) {
          0 -> ChooseSpreadPage(
            onSpreadSelected = { spread ->
              spreadInput = spread
              maxUnlockedPage = maxOf(maxUnlockedPage, 1)
              requestedPage = 1
            },
            isSpreadSelected = { spread ->
              spreadInput == spread
            }
          )
          1 -> ChooseTopicPage(
            onTopicSelected = { topic ->
              topicInput = topic
              maxUnlockedPage = maxOf(maxUnlockedPage, 2)
              requestedPage = 2
            },
            isTopicSelected = { topic ->
              topicInput == topic
            }
          )
          2 -> EnterQuestionPage(
            onDrawRunesClicked = { question ->
              viewModel.updateSelections(
                spreadInput!!,
                topicInput!!,
                question
              )
            }
          )
        }
      }
      TakaPagerIndicator(
        pageCount = 3,
        currentPage = pagerState.currentPage,
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom=TakaContentSpacing),
      )
    }
  }
}

@Preview
@Composable
fun ChooseSpreadPage(
  onSpreadSelected: (RuneSpread) -> Unit = {},
  isSpreadSelected: (RuneSpread) -> Boolean = { true }
) {
  FadingScrollColumn(
    modifier = Modifier.fillMaxSize()
      .padding(start = TakaScreenPadding, end = TakaScreenPadding),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top
  ) {
    Text(
      text = stringResource(Res.string.reading_choose_spread),
      style = MaterialTheme.typography.headlineMedium
    )
    RuneSpread.entries.forEach { spread ->
      Spacer(modifier = Modifier.padding(top = TakaSectionSpacing))
      TakaSelectableCard(
        onClick = { onSpreadSelected(spread) },
        isSelected = isSpreadSelected(spread),
        verticalArrangement = Arrangement.Center
      ) {
        Icon(
          painter = painterResource(spread.icon),
          contentDescription = stringResource(spread.title),
          modifier = Modifier
            .size(144.dp),
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
          text = stringResource(spread.title),
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
          modifier = Modifier.padding(top = TakaCardSpacing),
          text = stringResource(spread.description),
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }
  }
}

@Preview
@Composable
fun ChooseTopicPage(
  onTopicSelected: (ReadingTopic) -> Unit = {},
  isTopicSelected: (ReadingTopic) -> Boolean = { true }
) {
  FadingScrollColumn(
    modifier = Modifier.fillMaxSize()
      .padding(start = TakaScreenPadding, end = TakaScreenPadding),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top
  ) {
    Text(
      text = stringResource(Res.string.reading_choose_topic),
      style = MaterialTheme.typography.headlineMedium
    )
    ReadingTopic.entries.forEach { topic ->
      Spacer(modifier = Modifier.padding(top = TakaSectionSpacing))
      TakaSelectableCard(
        onClick = { onTopicSelected(topic) },
        isSelected = isTopicSelected(topic),
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.Top,
        ) {
          Icon(
            painter = painterResource(topic.icon),
            contentDescription = stringResource(topic.title),
            modifier = Modifier
              .size(72.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
          )
          Spacer(modifier = Modifier.width(TakaSpaceMd))
          Column(
            modifier = Modifier.weight(1f),
          ) {
            Text(
              text = stringResource(topic.title),
              style = MaterialTheme.typography.titleMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
              modifier = Modifier
                .padding(top = TakaContentSpacing),
              text = stringResource(topic.description),
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }
    }
  }
}

@Preview
@Composable
fun EnterQuestionPage(
  onDrawRunesClicked: (String) -> Unit = {}
) {
  var questionInput by rememberSaveable { mutableStateOf("") }
  FadingScrollColumn(
    modifier = Modifier.fillMaxSize()
      .padding(start = TakaScreenPadding, end = TakaScreenPadding),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top
  ) {
    Text(
      modifier = Modifier.align(Alignment.CenterHorizontally),
      text = stringResource(Res.string.reading_question_title),
      style = MaterialTheme.typography.headlineMedium    )
    Text(
      text = stringResource(Res.string.reading_question_description),
      style = MaterialTheme.typography.bodyMedium,
      modifier = Modifier.padding(top = TakaSectionSpacing)
    )
    TakaTextField(
      value = questionInput,
      onValueChange = { questionInput = it },
      label = stringResource(Res.string.reading_question_textfield_label),
      singleLine = true,
      modifier = Modifier.padding(top = TakaContentSpacing)
    )
    TakaButton(
      onClick = { onDrawRunesClicked(questionInput) },
      modifier = Modifier
        .padding(top = TakaSectionSpacing)
        .align(Alignment.CenterHorizontally)
    ) {
      Text(stringResource(Res.string.button_draw_runes))
    }
  }
}
