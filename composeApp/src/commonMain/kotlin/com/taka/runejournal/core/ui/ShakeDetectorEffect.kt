// commonMain/core/ui/ShakeDetectorEffect.kt
package com.taka.runejournal.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset

@Composable
expect fun ShakeDetectorEffect(
  onShakeImpulse: (direction: Offset, strength: Float) -> Unit,
  onShakingChanged: (isShaking: Boolean) -> Unit
)