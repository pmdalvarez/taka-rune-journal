package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.components.ButtonStyle
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaCard
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.button_edit
import taka_rune_journal.composeapp.generated.resources.rune_reading_notes_title

@Composable
fun ReadingInterpretationNotesTab(notes: String?) {
  TakaCard(
    modifier = Modifier
      .fillMaxHeight()
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

      TakaButton(
        style = ButtonStyle.Tertiary,
        onClick = {},
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
}