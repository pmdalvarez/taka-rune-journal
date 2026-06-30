package com.taka.runejournal.feature.reading.ui

import DeleteTimelineEntryDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarAction
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.feature.reading.ui.components.ReadingInterpretationContextHeader
import com.taka.runejournal.feature.reading.ui.components.ReadingInterpretationNotesTab
import com.taka.runejournal.feature.reading.ui.components.ReadingInterpretationRuneTab
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.delete_dialog_title_rune_reading

@Composable
fun ReadingInterpretationScreen(
  viewModel: ReadingInterpretationViewModel,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  val snackbarHostState = remember { SnackbarHostState() }
  val pagerState = rememberPagerState (
    pageCount = { uiState.tabs.size },
  )
  val coroutineScope = rememberCoroutineScope()

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
            title = stringResource(uiState.category.readingType()),
            navigationIcon = TakaTopBarNavigationIcon.Back,
            onNavigationClick = onBackClick,
            action = TakaTopBarAction.RuneInterpretationActions(
              onDeleteClick = { viewModel.openDeleteDialog() }
            )
          )
      }
  ) { contentModifier ->
    Column(
      modifier = contentModifier.fillMaxSize()
    ) {
      if (!uiState.question.isNullOrBlank()) {
        ReadingInterpretationContextHeader(
          question = uiState.question
        )
      }
      if (uiState.tabs.isNotEmpty()) {
        PrimaryTabRow(
          selectedTabIndex = pagerState.currentPage,
          containerColor = MaterialTheme.colorScheme.background,
          divider = {},
          // SecondaryIndicator needed so that indicator takes up full length of tab instead of the text
          indicator = {
              TabRowDefaults.SecondaryIndicator(
                modifier =  Modifier.tabIndicatorOffset(pagerState.currentPage, matchContentSize = false),
                color = MaterialTheme.colorScheme.primary,
              )
          }
        ) {
          uiState.tabs.forEachIndexed { index, tab ->
            Tab(
              selected = pagerState.currentPage == index,
              selectedContentColor = MaterialTheme.colorScheme.primary,
              unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
              onClick = {
                coroutineScope.launch {
                  pagerState.animateScrollToPage(index)
                }
              },
              text = { Text(stringResource(tab.label)) },
            )
          }
        }
        Spacer(modifier = Modifier.height(TakaContentSpacing))
        HorizontalPager(
          state = pagerState,
          modifier = Modifier.weight(1f),
        ) { page ->
          val tab = uiState.tabs[page]
          when (tab) {
            is ReadingInterpretationTab.Rune -> ReadingInterpretationRuneTab(
              tab.drawnRune,
              tab.interpretation,
              tab.supplementalInterpretation,
              tab.keywords,
              tab.supplementalKeywords
            )
            is ReadingInterpretationTab.Notes -> ReadingInterpretationNotesTab(
              tab.notes,
              onSaveClicked = viewModel::saveNotes
            )
          }
        }
      }
    }
  }

  if (uiState.showDeleteDialog) {
    DeleteTimelineEntryDialog(
      onDismiss = viewModel::dismissDeleteDialog,
      onConfirm = viewModel::deleteReading,
      stringResource(Res.string.delete_dialog_title_rune_reading),
      null
    )
  }
}

