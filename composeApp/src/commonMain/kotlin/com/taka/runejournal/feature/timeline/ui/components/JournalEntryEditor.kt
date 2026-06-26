package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.components.TakaTextField
import com.taka.runejournal.core.ui.theme.TakaFieldSpacing
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.new_journal_entry_textfield_label_notes
import taka_rune_journal.composeapp.generated.resources.new_journal_entry_textfield_label_title

@Composable
fun JournalEntryEditor(
  modifier: Modifier,
  titleValue: String,
  titleOnValueChange: (String) -> Unit,
  notesValue: String,
  notesOnValueChange: (String) -> Unit,
) {
  Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(TakaFieldSpacing),
  ) {
    TakaTextField(
      value = titleValue,
      onValueChange = titleOnValueChange,
      label = stringResource(Res.string.new_journal_entry_textfield_label_title),
      singleLine = true,
    )
    TakaTextField(
      value = notesValue,
      onValueChange = notesOnValueChange,
      label = stringResource(Res.string.new_journal_entry_textfield_label_notes),
      minLines = 5,
    )
  }
}