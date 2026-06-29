package com.taka.runejournal.feature.reading.ui

import DeleteTimelineEntryDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarAction
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.core.ui.theme.TakaScreenPadding
import com.taka.runejournal.feature.reading.ui.components.ReadingInterpretationContextHeader
import com.taka.runejournal.feature.reading.ui.components.RuneInterpretationTab
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
        is UiEvent.NavigateBack -> onBackClick()
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
      ReadingInterpretationContextHeader(
        question = uiState.question
      )

      PrimaryScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        edgePadding = TakaScreenPadding,
      ) {
        uiState.tabs.forEachIndexed { index, tab ->
          Tab(
            selected = pagerState.currentPage == index,
            onClick = {
              coroutineScope.launch {
                pagerState.animateScrollToPage(index)
              }
            },
            text = { Text(stringResource(tab.label)) },
          )
        }
      }

      HorizontalPager(
        state = pagerState,
        modifier = Modifier.weight(1f),
      ) { page ->
        val tab = uiState.tabs[page]
        when (tab) {
          is ReadingInterpretationTab.Rune -> RuneInterpretationTab()
          is ReadingInterpretationTab.Notes -> RuneInterpretationTab()
          else -> {}
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

