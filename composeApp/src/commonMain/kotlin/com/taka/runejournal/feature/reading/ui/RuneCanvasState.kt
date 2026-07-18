package com.taka.runejournal.feature.reading.ui

import androidx.compose.ui.geometry.Offset
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.feature.reading.domain.model.RuneVisualState
import kotlin.random.Random

class RuneCanvasState(
  canvasWidth: Float,
  canvasHeight: Float,
)
{
  val runeWidth: Float
  val runeHeight: Float
  val runeVisualStates: Map<Rune, RuneVisualState>

  init {
    runeWidth = canvasWidth * RUNE_WIDTH_TO_CANVAS_WIDTH_RATIO
    runeHeight = runeWidth / RUNE_HEIGHT_TO_WIDTH_RATIO
    runeVisualStates = randomizeRuneVisualStates(canvasWidth, canvasHeight)
  }

  private fun randomizeRuneVisualStates(
    width: Float,
    height: Float,
  ): Map<Rune, RuneVisualState> {
    // Paddings ensure rune is fully inside of canvas regardless of angle
    val horizontalPadding = runeWidth
    val verticalPadding = runeHeight
    return Rune.entries.associateWith {
      RuneVisualState(
        center = Offset(
          x = horizontalPadding + (Random.nextFloat() * (width - 2 * horizontalPadding)),
          y = verticalPadding + (Random.nextFloat() * (height - 2 * verticalPadding)),
        ),
        depth = Random.nextFloat(),
        angle = Random.nextFloat() * 360f,
      )
    }
  }

  companion object {
    private const val RUNE_HEIGHT_TO_WIDTH_RATIO = 1.5f
    private const val RUNE_WIDTH_TO_CANVAS_WIDTH_RATIO = 0.25f
  }
}

