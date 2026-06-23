package com.taka.runejournal.feature.timeline.ui

import DeleteTimelineEntryDialog
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.ButtonStyle
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarAction
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.core.ui.components.showInfoSnackbar
import com.taka.runejournal.feature.timeline.ui.components.ActionButtons
import com.taka.runejournal.feature.timeline.ui.components.GreetingSection
import com.taka.runejournal.feature.timeline.ui.components.TimelineItemRow
import org.jetbrains.compose.resources.getString

@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel,
    onAboutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onDesignPlaygroundClick: () -> Unit,
    onTimelineDetailClick: (Long) -> Unit,
    onNewReadingClick: () -> Unit,
    onNewJournalEntryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagingItems = viewModel.timelineItems.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowError -> { snackbarHostState.showErrorSnackbar(message = getString(event.messageRes)) }
                is UiEvent.ShowInfo -> { snackbarHostState.showInfoSnackbar(message = getString(event.messageRes)) }
                else -> {} // No other events expected
            }
        }
    }

    TakaScaffold (
        modifier = modifier,
        snackbarHost = { TakaSnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TakaTopBar(
                action = TakaTopBarAction.TimelineActions(
                    onNewReadingClick = onNewReadingClick,
                    onNewJournalEntryClick = onNewJournalEntryClick,
                    onSettingsClick = onSettingsClick,
                    onAboutClick = onAboutClick,
                    onDesignPlaygroundClick = onDesignPlaygroundClick
                ),
            )
        }
    ) { contentModifier ->
        LazyColumn(
            modifier = contentModifier.pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    },
                )
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                GreetingSection(
                    uiState.displayName,
                    uiState.dailyPrompt,
                    viewModel::initializeDailyPrompt,
                    viewModel::setDisplayName
                )
            }
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { it.id }
            ) { index ->
                val item = pagingItems[index]
                item?.let {
                    TimelineItemRow(
                        it,
                        onTimelineDetailClick,
                        viewModel::openDeleteDialog
                    )
                }
            }
            item { ActionButtons(onNewReadingClick, onNewJournalEntryClick) }
            item { TestArea(viewModel) }
        }
    }

    uiState.deleteDialogUiState?.let {
        DeleteTimelineEntryDialog(
            onDismiss = viewModel::dismissDeleteDialog,
            onConfirm = viewModel::deleteTimelineItem,
            it.name,
            it.type
        )
    }

}

@Composable
private fun TestArea(viewModel: TimelineViewModel) {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )

    TakaButton(
        onClick = { viewModel.setDisplayName("Paolo" + (0..100).random()) },
        style = ButtonStyle.Tertiary,
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("Change name to Paolo + random number")
    }

    TakaButton(
        onClick = { viewModel.setDisplayName("") },
        style = ButtonStyle.Tertiary,
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("Change name to empty")
    }

    TakaButton(
        onClick = { viewModel.createSingleRuneReading() },
        style = ButtonStyle.Tertiary,
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("New Single Rune Reading")
    }

    TakaButton(
        onClick = { viewModel.createPpfRuneReading() },
        style = ButtonStyle.Tertiary,
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("New PPF Reading")
    }

}