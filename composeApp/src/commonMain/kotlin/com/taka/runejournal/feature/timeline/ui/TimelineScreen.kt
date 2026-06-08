package com.taka.runejournal.feature.timeline.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.taka.runejournal.feature.timeline.ui.components.ActionButtons
import com.taka.runejournal.feature.timeline.ui.components.GreetingSection
import com.taka.runejournal.feature.timeline.ui.components.TimelineItemRow
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.timeline_textfield_label_your_name

@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel,
    onSettingsClick: () -> Unit,
    onTimelineDetailClick: (Long) -> Unit,
    onNewReadingClick: () -> Unit,
    onNewJournalEntryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagingItems = viewModel.timelineItems.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            GreetingSection(
                uiState.displayName,
                uiState.dailyPrompt,
                viewModel::initializeDailyPrompt
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
                    viewModel::deleteTimelineItem
                )
            }
        }
        item { ActionButtons(onNewReadingClick, onNewJournalEntryClick) }
        item { testArea(viewModel, onSettingsClick) }
    }
}

//@Composable
//fun DisplayNameTextField(
//    displayName: String,
//    onDisplayNameChanged: (String) -> Unit,
//    modifier: Modifier = Modifier,
//) {
////    var nameInput by rememberSaveable(displayName) {
////        mutableStateOf(displayName)
////    }
//
//    fun saveName() {
//        onDisplayNameChanged(nameInput.trim())
//    }
//
//    OutlinedTextField(
//        value = nameInput,
//        onValueChange = { nameInput = it },
//        label = {
//            Text(stringResource(Res.string.timeline_textfield_label_your_name))
//        },
//        singleLine = true,
//        keyboardOptions = KeyboardOptions(
//            imeAction = ImeAction.Done,
//        ),
//        keyboardActions = KeyboardActions(
//            onDone = {
//                saveName()
//            },
//        ),
//        modifier = modifier.onFocusChanged { focusState ->
//            if (!focusState.isFocused) {
//                saveName()
//            }
//        },
//    )
//}

@Composable
private fun testArea(viewModel: TimelineViewModel, onSettingsClick: () -> Unit) {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
    Button(
        onClick = onSettingsClick,
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("Open Settings (TODO Move to top bar)")
    }

    Button(
        onClick = { viewModel.setDisplayName("Paolo" + (0..100).random()) },
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("Change name to Paolo + random number")
    }

    Button(
        onClick = { viewModel.setDisplayName("") },
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("Change name to empty")
    }

    Button(
        onClick = { viewModel.createJournalEntry("This is a random journal entry with a random number: " + (0..100).random() , "Title" + (0..100).random()) },
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("Add random journal entry")
    }

}