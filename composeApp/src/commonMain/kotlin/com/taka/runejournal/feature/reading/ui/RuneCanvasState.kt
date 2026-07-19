package com.taka.runejournal.feature.reading.ui

import androidx.compose.ui.geometry.Offset
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.feature.reading.domain.model.RuneVisualState
import kotlin.random.Random

data class RuneCanvasState(
  private val canvasWidth: Float,
  private val canvasHeight: Float,
  val runeWidth: Float = canvasWidth * RUNE_WIDTH_TO_CANVAS_WIDTH_RATIO,
  val runeHeight: Float = runeWidth / RUNE_HEIGHT_TO_WIDTH_RATIO,
  val runeVisualStates: Map<Rune, RuneVisualState> = randomizeRuneVisualStates(canvasWidth, canvasHeight, runeWidth, runeHeight)
)
{
  private val shakeStrengthMultiplier: Float =
    canvasWidth * STRONG_SHAKE_CANVAS_WIDTH_RATIO / STRONG_SHAKE_STRENGTH
  private val angleMovementMultiplier: Float =
    STRONG_SHAKE_ANGLE_DEGREES / STRONG_SHAKE_STRENGTH

  fun applyShakeImpulse(
    direction: Offset,
    strength: Float,
  ): RuneCanvasState {
    val movementDistance = strength * shakeStrengthMultiplier
    val updatedRuneVisualStates = runeVisualStates.mapValues { (_, visualState) ->
      val runeMovementMultiplier = Random.nextDouble(0.8, 1.0).toFloat()
      val trayEffectDirection = Offset(
        x = -direction.x + Random.nextDouble(-DIRECTION_VARIATION, DIRECTION_VARIATION).toFloat(),
        y = -direction.y + Random.nextDouble(-DIRECTION_VARIATION, DIRECTION_VARIATION).toFloat(),
      ).normalized()
      val movement = trayEffectDirection * movementDistance * runeMovementMultiplier
      visualState.copy(
        center = bounceRuneInsideCanvas(visualState.center + movement),
        angle = (visualState.angle + calculateAngleChange(strength)).normalizedDegrees(),
      )
    }
    return copy(
      runeVisualStates = updatedRuneVisualStates
    )
  }

  private fun bounceRuneInsideCanvas(
    center: Offset,
  ): Offset {
    val minX = runeWidth / 2f
    val maxX = canvasWidth - runeWidth / 2f
    val minY = runeHeight / 2f
    val maxY = canvasHeight - runeHeight / 2f

    return Offset(
      x = bounceInsideBounds(
        value = center.x,
        min = minX,
        max = maxX,
      ),
      y = bounceInsideBounds(
        value = center.y,
        min = minY,
        max = maxY,
      ),
    )
  }

  private fun bounceInsideBounds(
    value: Float,
    min: Float,
    max: Float,
  ): Float {
    val bouncedValue = when {
      value < min -> {
        val overflow = min - value
        min + overflow * BOUNCE_STRENGTH_MULTIPLIER
      }

      value > max -> {
        val overflow = value - max
        max - overflow * BOUNCE_STRENGTH_MULTIPLIER
      }

      else -> value
    }

    return bouncedValue.coerceIn(min, max)
  }

  private fun Offset.normalized(): Offset {
    val distance = getDistance()

    return if (distance == 0f) {
      Offset.Zero
    } else {
      this / distance
    }
  }

  private fun Float.normalizedDegrees(): Float =
    ((this % 360f) + 360f) % 360f

  private fun calculateAngleChange(strength: Float): Float {
    val rotationFactor = Random.nextDouble(-1.0, 1.0).toFloat()
    return rotationFactor * strength * angleMovementMultiplier
  }

  companion object {
    private const val RUNE_HEIGHT_TO_WIDTH_RATIO = 1.5f
    private const val RUNE_WIDTH_TO_CANVAS_WIDTH_RATIO = 0.25f
    private const val STRONG_SHAKE_STRENGTH = 12f // This is the shake strength we define as a strong shake
    private const val STRONG_SHAKE_CANVAS_WIDTH_RATIO = 0.3f // This is the % of the canvas width the rune should move from a strong shake
    private const val STRONG_SHAKE_ANGLE_DEGREES = 45f // This is the angle change that should come from as a strong shake
    private const val BOUNCE_STRENGTH_MULTIPLIER = 0.3f // if rune bounces of edge is moves back but at the strength of this multiplier
    private const val DIRECTION_VARIATION = 0.35

    private fun randomizeRuneVisualStates(
      width: Float,
      height: Float,
      runeWidth: Float,
      runeHeight: Float,
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

  }
}

