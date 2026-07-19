package com.taka.runejournal.feature.reading.ui

import androidx.compose.runtime.Composable

expect class RuneHapticFeedback {
  fun playStoneClink(strength: Float)
}

@Composable
expect fun rememberRuneHapticFeedback(): RuneHapticFeedback