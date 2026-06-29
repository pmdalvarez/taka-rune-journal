package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.components.TakaCard

@Composable
fun ReadingInterpretationNotesTab(notes: String?) {
  TakaCard(
    modifier = Modifier
      .fillMaxHeight()
  ) {
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = notes ?: "No notes yet",
      style = MaterialTheme.typography.bodyMedium
    )
  }
}