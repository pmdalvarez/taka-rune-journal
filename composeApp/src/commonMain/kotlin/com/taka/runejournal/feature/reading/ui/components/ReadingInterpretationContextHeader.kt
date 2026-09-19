package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceMd
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_question_title

@Preview
@Composable
fun ReadingInterpretationContextHeader(
  question: String = "What is the meaning of life?",
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(IntrinsicSize.Min),
  ) {
    VerticalDivider(
      modifier = Modifier.fillMaxHeight(),
      thickness = 2.dp,
      color = MaterialTheme.colorScheme.outlineVariant,
    )

    Column(
      modifier = Modifier.padding(start = TakaSpaceMd)
    ) {
      Text(
        text = stringResource(Res.string.reading_question_title),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Text(
        text = question,
        style = MaterialTheme.typography.titleMedium,
      )
    }
  }
}
