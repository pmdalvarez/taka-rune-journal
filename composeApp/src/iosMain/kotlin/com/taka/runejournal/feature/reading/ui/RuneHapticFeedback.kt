package com.taka.runejournal.feature.reading.ui

import androidx.compose.runtime.Composable

actual class RuneHapticFeedback{
  actual fun playStoneClink(strength: Float) {
    // TODO implement for ios
  }
}

@Composable
actual fun rememberRuneHapticFeedback(): RuneHapticFeedback {
  return RuneHapticFeedback()
}