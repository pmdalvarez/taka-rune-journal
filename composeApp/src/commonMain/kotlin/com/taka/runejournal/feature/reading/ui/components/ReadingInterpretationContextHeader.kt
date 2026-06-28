package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.theme.TakaSpaceXs

@Composable
fun ReadingInterpretationContextHeader(
  question: String?,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(TakaSpaceXs),
  ) {
    if (!question.isNullOrBlank()) {
      Text(
        text = question,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}
