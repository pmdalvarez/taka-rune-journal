package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.FadingScrollColumn
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaCard
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaIconButtonSize
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_generating_pdf_button
import taka_rune_journal.composeapp.generated.resources.reading_share_preview_button
import taka_rune_journal.composeapp.generated.resources.reading_share_prompt
import taka_rune_journal.composeapp.generated.resources.reading_share_title

@Composable
fun ReadingInterpretationShareTab(recipient: String, isGeneratingPdf: Boolean, onPreviewClicked: () -> Unit) {

  TakaCard(
    modifier = Modifier
      .fillMaxHeight()
  ) {
    FadingScrollColumn {
      Text(
        text = stringResource(Res.string.reading_share_title, recipient),
        style = MaterialTheme.typography.titleMedium,
      )
      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = TakaContentSpacing),
        text = stringResource(Res.string.reading_share_prompt, recipient),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      TakaButton(
        onClick = onPreviewClicked,
        enabled = !isGeneratingPdf,
        modifier = Modifier
          .padding(top = TakaContentSpacing)
          .align(Alignment.CenterHorizontally)
      ) {
        if (isGeneratingPdf) {
          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            CircularProgressIndicator(
              modifier = Modifier.size(TakaIconButtonSize),
              strokeWidth = 2.dp,
              color = MaterialTheme.colorScheme.onPrimary,
            )
            Text(stringResource(Res.string.reading_generating_pdf_button))
          }
        } else {
          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Icon(
              imageVector = Icons.Default.Visibility,
              contentDescription = null,
              modifier = Modifier.size(TakaIconButtonSize),
              tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(stringResource(Res.string.reading_share_preview_button))
          }
        }
      }
    }
  }
}