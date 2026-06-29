package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.ui.components.TakaCard
import org.jetbrains.compose.resources.StringResource

@Composable
fun ReadingInterpretationNotesTab(notes: String?) {
  TakaCard {
    Text(
      text = notes ?: "No notes yet",
      style = MaterialTheme.typography.headlineMedium
    )
  }
}