package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.ButtonStyle
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaCard
import com.taka.runejournal.core.ui.components.TakaTextAction
import com.taka.runejournal.core.ui.components.TakaTextField
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceMd
import com.taka.runejournal.core.ui.theme.TakaSpaceSm
import com.taka.runejournal.core.ui.theme.TakaSpaceXs
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.button_add_notes
import taka_rune_journal.composeapp.generated.resources.button_cancel
import taka_rune_journal.composeapp.generated.resources.button_edit
import taka_rune_journal.composeapp.generated.resources.button_save
import taka_rune_journal.composeapp.generated.resources.rune_reading_notes_add_prompt
import taka_rune_journal.composeapp.generated.resources.rune_reading_notes_texfield_label
import taka_rune_journal.composeapp.generated.resources.rune_reading_notes_title

@Composable
fun ReadingInterpretationNotesTab(notes: String?, onSaveClicked: (String) -> Unit) {
  var isEditing by rememberSaveable { mutableStateOf(false) }
  var notesInput by rememberSaveable(notes) { mutableStateOf(notes.orEmpty()) }

  TakaCard(
    modifier = Modifier
      .fillMaxHeight()
  ) {
    when {
      isEditing -> NotesTabEditMode(
        value = notesInput,
        onValueChange = { notesInput = it },
        onCancelClicked = {
          notesInput = notes ?: ""
          isEditing = false
        },
        onSaveClicked = {
          onSaveClicked(notesInput)
          notesInput = notes ?: ""
          isEditing = false
        }
      )
      !notes.isNullOrBlank() -> {
        NotesTabViewMode(
          notes = notes,
          onEditClicked = { isEditing = true }
        )
      }
      else ->
        NotesTabViewModeEmptyNotes(onEditClicked = { isEditing = true })
    }
  }
}

@Composable fun NotesTabViewMode(notes: String, onEditClicked: () -> Unit) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = stringResource(Res.string.rune_reading_notes_title),
      style = MaterialTheme.typography.titleMedium,
    )
    TakaTextAction(
      text = stringResource(Res.string.button_edit),
      onClick = onEditClicked,
    )

  }
  Text(
    modifier = Modifier.fillMaxWidth().padding(top = TakaContentSpacing),
    text = notes,
    style = MaterialTheme.typography.bodyMedium
  )
}

@Composable fun NotesTabViewModeEmptyNotes(onEditClicked: () -> Unit) {
  Text(
    text = stringResource(Res.string.rune_reading_notes_title),
    style = MaterialTheme.typography.titleMedium,
  )
  Text(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = TakaContentSpacing),
    text = stringResource(Res.string.rune_reading_notes_add_prompt),
    style = MaterialTheme.typography.bodyMedium,
    color = MaterialTheme.colorScheme.onSurfaceVariant,
  )
  TakaTextAction(
    modifier = Modifier
      .padding(top = TakaContentSpacing),
    text = stringResource(Res.string.button_add_notes),
    onClick = onEditClicked,
  )
}

@Composable fun NotesTabEditMode(
  value: String,
  onValueChange: (String) -> Unit,
  onCancelClicked: () -> Unit,
  onSaveClicked: () -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = stringResource(Res.string.rune_reading_notes_title),
      style = MaterialTheme.typography.titleMedium,
    )
    Row(horizontalArrangement = Arrangement.spacedBy(TakaSpaceMd)) {
      TakaTextAction(
        text = stringResource(Res.string.button_cancel),
        onClick = onCancelClicked,
      )
      TakaTextAction(
        text = stringResource(Res.string.button_save),
        onClick = onSaveClicked,
      )
    }
  }
  TakaTextField(
    value = value,
    onValueChange = onValueChange,
    minLines = 5,
    label = stringResource(Res.string.rune_reading_notes_texfield_label),
  )
}