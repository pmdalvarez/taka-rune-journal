package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.ui.components.TakaCard
import com.taka.runejournal.feature.reading.ui.toDotSeparatedKeywords
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ReadingInterpretationRuneTab(
  drawnRune: DrawnRune,
  interpretation: StringResource,
  supplementalInterpretation: StringResource?,
  keywords: StringResource,
  supplementalKeywords: StringResource?
) {
  TakaCard(
    modifier = Modifier
      .fillMaxHeight()
  ) {
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = drawnRune.rune.displayName,
      style = MaterialTheme.typography.headlineSmall,
      textAlign = TextAlign.Center,
    )
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(keywords).toDotSeparatedKeywords(),
      style = MaterialTheme.typography.bodyMedium,
      textAlign = TextAlign.Center,
    )
  }
}