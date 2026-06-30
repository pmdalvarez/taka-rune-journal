package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.foundation.layout.Arrangement
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
import com.taka.runejournal.core.ui.components.ButtonStyle
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaCard
import com.taka.runejournal.core.ui.components.TakaTextField
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceXs
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.button_cancel
import taka_rune_journal.composeapp.generated.resources.button_edit
import taka_rune_journal.composeapp.generated.resources.button_save
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
    if (isEditing) {
      NotesTabEditMode(
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
    } else {
      NotesTabViewMode(
        notes = notes,
        onEditClicked = { isEditing = true }
      )
    }
  }
}

@Composable fun NotesTabViewMode(notes: String?, onEditClicked: () -> Unit) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = stringResource(Res.string.rune_reading_notes_title),
      style = MaterialTheme.typography.titleMedium,
    )

    TakaButton(
      style = ButtonStyle.Tertiary,
      onClick = onEditClicked,
    ) {
      Text(stringResource(Res.string.button_edit))
    }
  }

  Text(
    modifier = Modifier.fillMaxWidth().padding(top = TakaContentSpacing),
    text = notes ?: "No notes yet",
    style = MaterialTheme.typography.bodyMedium
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

    Row(horizontalArrangement = Arrangement.spacedBy(TakaSpaceXs)) {
      TakaButton(
        style = ButtonStyle.Tertiary,
        onClick = onCancelClicked,
      ) {
        Text(stringResource(Res.string.button_cancel))
      }
      TakaButton(
        style = ButtonStyle.Tertiary,
        onClick = onSaveClicked,
      ) {
        Text(stringResource(Res.string.button_save))
      }
    }
  }

  TakaTextField(
    value = value,
    onValueChange = onValueChange,
    label = stringResource(Res.string.rune_reading_notes_texfield_label),
  )
}