package com.taka.runejournal.feature.reading.ui

import androidx.compose.ui.geometry.Offset
import com.taka.runejournal.core.domain.model.Rune
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class RuneCanvasState(
  private val canvasWidth: Float,
  private val canvasHeight: Float,
  private val requiredRuneCount: Int,
  val runeWidth: Float = canvasWidth * RUNE_WIDTH_TO_CANVAS_WIDTH_RATIO,
  val runeHeight: Float = runeWidth * RUNE_HEIGHT_TO_WIDTH_RATIO,
  val runeVisualStates: Map<Rune, RuneVisualState> = randomizeRuneVisualStates(canvasWidth, canvasHeight, runeWidth, runeHeight),
  val draggedRuneState: DraggedRuneState? = null,
  val selectedRunes: List<Rune> = emptyList(),
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

println("XXXXXXXXXXXXXXXXXXXXXX movementDistance: $movementDistance, strength: $strength, shakeStrengthMultiplier: $shakeStrengthMultiplier")

    val updatedRuneVisualStates = runeVisualStates.mapValues { (rune, visualState) ->
      if (isSelected(rune)) return@mapValues visualState

      val runeMovementMultiplier = Random.nextDouble(0.5, 1.0).toFloat()
      val trayEffectDirection = Offset(
        x = direction.x,
        y = direction.y,
      ).rotatedByDegrees(
        degrees = Random.nextDouble(
          -MAX_DIRECTION_VARIATION_DEGREES.toDouble(),
          MAX_DIRECTION_VARIATION_DEGREES.toDouble(),
        ).toFloat(),
      )
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

  fun centerSelectedRunes(): RuneCanvasState {
    val updatedRuneVisualStates = runeVisualStates.mapValues { (rune, visualState) ->
      if (!selectedRunes.contains(rune)) return@mapValues visualState
      val center = Offset(
        x = visualState.center.x,
        y = canvasHeight / 2f,
      )
      visualState.copy(
        center = center,
        angle = visualState.angle.snapToVertical(),
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
        println("XXXXXXXXXXXXXXXXXXXXXX bounced val: $value, new val: ${min + overflow * BOUNCE_STRENGTH_MULTIPLIER}")
        min + overflow * BOUNCE_STRENGTH_MULTIPLIER
      }

      value > max -> {
        val overflow = value - max
        println("XXXXXXXXXXXXXXXXXXXXXX bounced val: $value, new val: ${max - overflow * BOUNCE_STRENGTH_MULTIPLIER}")
        max - overflow * BOUNCE_STRENGTH_MULTIPLIER
      }

      else -> value
    }

    return bouncedValue.coerceIn(min, max)
  }

  private fun calculateAngleChange(strength: Float): Float {
    val rotationFactor = Random.nextDouble(-1.0, 1.0).toFloat()
    return rotationFactor * strength * angleMovementMultiplier
  }

  fun startDraggingRune(position: Offset): RuneCanvasState {
println("XXXXXXXXXXXXXXXXXXXXXX startDraggingRune position: $position")
    val (rune, visualState) = findTouchedRune(position, false) ?: return this
    val fingerFromCenter = position - visualState.center
    val nextTopDepth = runeVisualStates.values.maxOf { it.depth } + 1f // ensures dragged rune is over all other runes

    return copy(
      draggedRuneState = DraggedRuneState(
        rune = rune,
        anchorFromCenter = fingerFromCenter.rotatedByDegrees(-visualState.angle),
        initialFingerAngle = fingerFromCenter.angleDegrees(),
        initialRuneAngle = visualState.angle,
      ),
      runeVisualStates = runeVisualStates + (
          rune to visualState.copy(
            depth = nextTopDepth,
          )
        ),
    )
  }

  private fun findTouchedRune(position: Offset, includeSelectedRunes: Boolean = true): Map.Entry<Rune, RuneVisualState>? =
    runeVisualStates
      .entries
      .sortedByDescending { it.value.depth }
      .find { (rune, visualState) ->
        if (!includeSelectedRunes && selectedRunes.contains(rune)) {
          false
        } else  {
          position.isInsideRune(visualState, runeWidth, runeHeight)
        }
      }

  fun dragRuneToPosition(position: Offset): RuneCanvasState {
println("XXXXXXXXXXXXXXXXXXXXXX dragRuneToPosition position: $position")
    val dragState = draggedRuneState ?: return this
    val visualState = runeVisualStates[dragState.rune] ?: return this

    val fingerFromCenter = position - visualState.center
    val currentFingerAngle = fingerFromCenter.angleDegrees()

    val angleChange = currentFingerAngle - dragState.initialFingerAngle

    val newAngle = (dragState.initialRuneAngle + angleChange).normalizedDegrees()

    val rotatedAnchorFromCenter =
      dragState.anchorFromCenter.rotatedByDegrees(newAngle)

    val newCenter = position - rotatedAnchorFromCenter


    return copy(
      runeVisualStates = runeVisualStates + (
          dragState.rune to visualState.copy(
            center = clampRuneInsideCanvas(newCenter),
            angle = newAngle
          )
        ),
    )
  }

  fun stopDraggingRune(): RuneCanvasState =
    copy(
      draggedRuneState = null,
    )

  private fun clampRuneInsideCanvas(
    center: Offset,
  ): Offset {
    val minX = runeWidth / 2f
    val maxX = canvasWidth - runeWidth / 2f
    val minY = runeHeight / 2f
    val maxY = canvasHeight - runeHeight / 2f

    return Offset(
      x = center.x.coerceIn(minX, maxX),
      y = center.y.coerceIn(minY, maxY),
    )
  }

  fun toggleRuneSelectionAtPosition(position: Offset): RuneCanvasState {
println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX toggleRuneSelectionAtPosition position: $position")
    val (rune, visualState) = findTouchedRune(position) ?: return this
    when {
      selectedRunes.contains(rune) -> {
println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX toggleRuneSelectionAtPosition deselecting rune : $rune")
        return copy(
          selectedRunes = selectedRunes - rune,
        )
      }
      selectedRunes.size < requiredRuneCount -> {
println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX toggleRuneSelectionAtPosition selecting rune : $rune")
        val nextTopDepth = runeVisualStates.values.maxOf { it.depth } + 1f // ensures dragged rune is over all other runes
        return copy(
          selectedRunes = selectedRunes + rune,
          runeVisualStates = runeVisualStates + (
            rune to visualState.copy(
              depth = nextTopDepth,
            )
          ),
        )
      }
      else -> return this
    }
  }

  fun isSelected(rune: Rune): Boolean = selectedRunes.contains(rune)

  companion object {
    const val RUNE_SELECTION_ANIMATION_MILLIS = 420L
    const val RUNE_REVEAL_ANIMATION_MILLIS = 1000L
    const val IMPULSE_INTERVAL_MILLIS = 120L
    private const val RUNE_HEIGHT_TO_WIDTH_RATIO = 1.5f
    private const val RUNE_WIDTH_TO_CANVAS_WIDTH_RATIO = 0.25f
    private const val STRONG_SHAKE_STRENGTH = 15f // This is the shake strength we define as a strong shake
    private const val STRONG_SHAKE_CANVAS_WIDTH_RATIO = 0.6f // This is the % of the canvas width the rune should move from a strong shake
    private const val STRONG_SHAKE_ANGLE_DEGREES = 20f // This is the max angle change that should come from as a strong shake
    private const val MAX_DIRECTION_VARIATION_DEGREES = 45f // max angle change of direction when shaking rune
    private const val BOUNCE_STRENGTH_MULTIPLIER = 1.5f // if rune bounces of edge is moves back but at the strength of this multiplier

    private fun randomizeRuneVisualStates(
      width: Float,
      height: Float,
      runeWidth: Float,
      runeHeight: Float,
    ): Map<Rune, RuneVisualState> {
      // Below ensures we don't begin with (0,0) positions for all runes which affects initial animation
      if (width == 0f || height == 0f) return emptyMap()

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


private fun Offset.rotatedByDegrees(degrees: Float): Offset {
  val radians = degrees * PI.toFloat() / 180f
  val cos = cos(radians)
  val sin = sin(radians)

  return Offset(
    x = x * cos - y * sin,
    y = x * sin + y * cos,
  )
}

private fun Float.normalizedDegrees(): Float =
  ((this % 360f) + 360f) % 360f

private fun Offset.angleDegrees(): Float =
  atan2(y, x) * 180f / PI.toFloat()

private fun Offset.isInsideRune(visualState: RuneVisualState, runeWidth: Float, runeHeight: Float): Boolean {
  val touchFromCenter = this - visualState.center

  val unrotatedTouchFromCenter =
    touchFromCenter.rotatedByDegrees(-visualState.angle)

  return unrotatedTouchFromCenter.x in -runeWidth / 2f..runeWidth / 2f &&
      unrotatedTouchFromCenter.y in -runeHeight / 2f..runeHeight / 2f
}

private fun Float.snapToVertical(): Float = when {
  this > 270f -> 360f
  this > 90f && this <= 270f -> 180f
  else -> 0f
}
