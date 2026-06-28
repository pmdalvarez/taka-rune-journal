package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.domain.model.RuneOrientation
import com.taka.runejournal.core.ui.theme.TakaCardPadding
import com.taka.runejournal.core.ui.theme.TakaCardSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceMd
import com.taka.runejournal.feature.timeline.ui.TimelineItemUiModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.delete_dialog_title_journal_entry
import taka_rune_journal.composeapp.generated.resources.delete_dialog_title_rune_reading
import taka_rune_journal.composeapp.generated.resources.rune_display_name_reversed
import taka_rune_journal.composeapp.generated.resources.timeline_item_title_no_question
import taka_rune_journal.composeapp.generated.resources.timeline_item_title_untitled

@Composable
fun TimelineItemRow(
  item: TimelineItemUiModel,
  onJournalEntryClick: (Long) -> Unit,
  onRuneReadingClick: (Long) -> Unit,
  onDeleteClick: (Long, String, String?) -> Unit,
  modifier: Modifier = Modifier,
) {
  val title = when {
    !item.title.isNullOrBlank() -> item.title
    item.isJournalEntry -> stringResource(Res.string.timeline_item_title_untitled)
    else -> stringResource(Res.string.timeline_item_title_no_question)
  }
  val label = item.formattedDate + " · " + stringResource(item.typeRes)
  val deleteDialogTitle = if (item.isJournalEntry) {
    stringResource(Res.string.delete_dialog_title_journal_entry)
  } else {
    stringResource(Res.string.delete_dialog_title_rune_reading)
  }
  val bodyText = if (item.isJournalEntry) {
    item.journalNotesPreview
  } else {
    // concatenated rune string e.g. Fehu · Raidho (R) · Algiz
    item.drawnRunes?.let {
      buildString {
        it.forEachIndexed { index, drawnRune ->
          if (index > 0) append(" · ")
          if (drawnRune.orientation == RuneOrientation.REVERSED) {
            append(stringResource(Res.string.rune_display_name_reversed, drawnRune.rune.displayName))
          } else {
            append(drawnRune.rune.displayName)
          }
        }
      }
    }
  }
  val deleteDialogPreview = if (item.journalNotesPreview.isNullOrBlank()) {
    title
  } else {
    title + "\n\n" + item.journalNotesPreview
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = TakaCardSpacing)
      .combinedClickable(
        onClick = { if (item.isJournalEntry) onJournalEntryClick(item.id) else onRuneReadingClick(item.id) },
        onLongClick = { onDeleteClick(item.id, deleteDialogTitle, deleteDialogPreview) },
      ),
    shape = MaterialTheme.shapes.medium,
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainer,
      contentColor = MaterialTheme.colorScheme.onSurface,
    ),
    border = BorderStroke(
      width = 1.dp,
      color = MaterialTheme.colorScheme.outlineVariant,
    ),
    elevation = CardDefaults.cardElevation(
      defaultElevation = 0.dp,
      pressedElevation = 0.dp,
      focusedElevation = 0.dp,
      hoveredElevation = 0.dp,
    ),
  ) {
    Column(
      modifier = Modifier.fillMaxWidth().padding(TakaCardPadding),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
      ) {
        Icon(
          painter = painterResource(item.icon),
          contentDescription = title,
          modifier = Modifier
            .padding(top = 1.dp)
            .size(36.dp),
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.width(TakaSpaceMd))

        Column(
          modifier = Modifier.weight(1f),
        ) {
          Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )

          Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }

      if (!bodyText.isNullOrBlank()) {
        Text(
          text = bodyText,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = TakaSpaceMd),
        )
      }
    }
  }
}