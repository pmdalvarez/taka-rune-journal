package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
  TakaCard {
    Text(
      text = drawnRune.rune.displayName,
      style = MaterialTheme.typography.headlineMedium
    )
    Text(
      text = stringResource(keywords).toDotSeparatedKeywords(),
      style = MaterialTheme.typography.headlineMedium
    )
  }
}