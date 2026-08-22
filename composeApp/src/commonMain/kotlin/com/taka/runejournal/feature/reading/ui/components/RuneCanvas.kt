package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.taka.runejournal.core.ui.drawable
import com.taka.runejournal.core.ui.glowingDrawable
import com.taka.runejournal.feature.reading.ui.DrawState
import com.taka.runejournal.feature.reading.ui.RuneCanvasState
import com.taka.runejournal.feature.reading.ui.RuneImageDrawState
import org.jetbrains.compose.resources.imageResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.rune_empty
import taka_rune_journal.composeapp.generated.resources.rune_empty_glowing
import taka_rune_journal.composeapp.generated.resources.rune_empty_half_glowing
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.math.roundToInt

@Composable
fun RuneCanvas(
  modifier: Modifier = Modifier,
  runeCanvasState: RuneCanvasState,
  drawState: DrawState,
  onRuneDragStart: (touchPosition: Offset) -> Unit,
  onRuneDrag: (position: Offset) -> Unit,
  onRuneDragStop: () -> Unit,
  onRuneTap: (position: Offset) -> Unit,
) {
  val isGestureEnabled = !(drawState == DrawState.Choose.Shaking || drawState is DrawState.Reveal)
  val emptyRuneImage = imageResource(Res.drawable.rune_empty)
  val emptyRuneImageGlowing = imageResource(Res.drawable.rune_empty_glowing)
  val emptyRuneImageHalfGlowing = imageResource(Res.drawable.rune_empty_half_glowing)
  val runeImageDrawStates = buildList {
    runeCanvasState.runeVisualStates
      .entries
      .sortedBy { it.value.depth }
      .forEach { (rune, visualState) ->
        key(rune) {
          val animatedCenter by animateOffsetAsState(
            targetValue = visualState.center,
            animationSpec = tween(
              durationMillis = if (drawState is DrawState.Choose)
                RuneCanvasState.IMPULSE_INTERVAL_MILLIS.toInt()
              else
                RuneCanvasState.RUNE_REVEAL_ANIMATION_MILLIS.toInt(),
              easing = LinearOutSlowInEasing
            ),
            label = "Rune ${rune.name} Center",
          )
          val animatedAngle by animateFloatAsState(
            targetValue = visualState.angle,
            animationSpec = tween(
              durationMillis = if (drawState is DrawState.Choose)
                RuneCanvasState.IMPULSE_INTERVAL_MILLIS.toInt()
              else
                RuneCanvasState.RUNE_REVEAL_ANIMATION_MILLIS.toInt(),
              easing = LinearOutSlowInEasing
            ),
            label = "Rune ${rune.name} Angle",
          )
          val isDraggedRune = runeCanvasState.draggedRuneState?.rune == rune
          val isSelected = runeCanvasState.isSelected(rune)
          val emptyRuneProgress by animateFloatAsState(
            targetValue = if (drawState is DrawState.Choose) 1f else 0f,
            animationSpec = tween(durationMillis = RuneCanvasState.RUNE_SELECTION_ANIMATION_MILLIS.toInt(), easing = LinearOutSlowInEasing),
            label = "Selected Rune ${rune.name} fade in animation",
          )
          val glowingEmptyRuneProgress by animateFloatAsState(
            targetValue = when (drawState) {
              is DrawState.Choose -> if (isSelected) 1f else 0f
              is DrawState.Reveal.CenteringRunes -> if (isSelected) 1f else 0f
              is DrawState.Reveal.UnveilingGlyphs -> 0f
              is DrawState.Reveal.CompletingAnimations -> 0f
            },
            animationSpec = tween(durationMillis = RuneCanvasState.RUNE_SELECTION_ANIMATION_MILLIS.toInt(), easing = LinearOutSlowInEasing),
            label = "Selected Rune ${rune.name} fade in animation",
          )
          val glowingGlowingGlyphRuneProgress by animateFloatAsState(
            targetValue = if (drawState is DrawState.Reveal.UnveilingGlyphs && isSelected) 1f else 0f,
            animationSpec = tween(durationMillis = RuneCanvasState.RUNE_REVEAL_ANIMATION_MILLIS.toInt(), easing = LinearOutSlowInEasing),
            label = "Selected Rune ${rune.name} fade in animation",
          )
          val glyphRuneProgress by animateFloatAsState(
            targetValue = if (drawState is DrawState.Reveal.CompletingAnimations && isSelected) 1f else 0f,
            animationSpec = tween(durationMillis = RuneCanvasState.RUNE_REVEAL_ANIMATION_MILLIS.toInt(), easing = LinearOutSlowInEasing),
            label = "Selected Rune ${rune.name} fade in animation",
          )
          val drawCenter = if (isDraggedRune) visualState.center else animatedCenter
          val drawAngle = if (isDraggedRune) visualState.angle else animatedAngle

          // Unselected Rune
          add(RuneImageDrawState(image = emptyRuneImage, center =  drawCenter, angle = drawAngle, alpha = emptyRuneProgress))
          // Selected Rune - dragged rune cannot be in selected state
          if (!isDraggedRune) { add(RuneImageDrawState(image = emptyRuneImageHalfGlowing, center =  drawCenter, angle = drawAngle, alpha = glowingEmptyRuneProgress)) }
          // Rune being unveiled - has glowing glyph
          add(RuneImageDrawState(image = imageResource(rune.glowingDrawable()), center =  drawCenter, angle = drawAngle, alpha = glowingGlowingGlyphRuneProgress))
          // Rune fully unveiled- has non-glowing glyph
          add(RuneImageDrawState(image = imageResource(rune.drawable()), center =  drawCenter, angle = drawAngle, alpha = glyphRuneProgress))
        }
      }
  }
  val zoom by animateFloatAsState(
    targetValue = when {
      drawState is DrawState.Choose.Shaking -> RuneCanvasState.ZOOM_SHAKING
      drawState is DrawState.Reveal -> RuneCanvasState.ZOOM_REVEAL
      else -> 1f
    },
    animationSpec = tween(
      durationMillis = 200,
      easing = LinearOutSlowInEasing,
    ),
    label = "Rune Canvas Zoom",
  )

  val currentOnRuneDragStart by rememberUpdatedState(onRuneDragStart)
  val currentOnRuneDrag by rememberUpdatedState(onRuneDrag)
  val currentOnRuneDragStop by rememberUpdatedState(onRuneDragStop)
  val currentOnRuneTap by rememberUpdatedState(onRuneTap)
  Canvas(
    modifier = modifier.pointerInput(isGestureEnabled) {
      if (!isGestureEnabled) return@pointerInput
      detectDragGestures(
        onDragStart = { position ->
          currentOnRuneDragStart(position)
        },
        onDrag = { change, _ ->
          change.consume()
          currentOnRuneDrag(change.position)
        },
        onDragEnd = {
          currentOnRuneDragStop()
        },
        onDragCancel = {
          currentOnRuneDragStop()
        }
      )
    }.pointerInput(isGestureEnabled) {
      if (!isGestureEnabled) return@pointerInput
      detectTapGestures(
        onTap = { position ->
          currentOnRuneTap(position)
        }
      )
    }
  ) {
    scale(
      scale = zoom,
      pivot = center,
    ) {
      runeImageDrawStates
        .forEach { runeDrawState ->
          if (runeDrawState.alpha != 0f) { // skip drawing this if invisible
            drawRune(
              runeImage = runeDrawState.image,
              center = runeDrawState.center,
              angle = runeDrawState.angle,
              alpha = runeDrawState.alpha,
              runeWidth = runeCanvasState.runeWidth,
              runeHeight = runeCanvasState.runeHeight
            )
          }
        }
    }
  }
}

private fun DrawScope.drawRune(
  runeImage: ImageBitmap,
  center: Offset, // Center point of the rune within the canvas, in pixels.
  angle: Float, //  Rotation angle in degrees.
  alpha: Float = 1f, // For crossfading if rune is being selected or selected, represents alpha of unselected image
  runeWidth: Float,
  runeHeight: Float,
) {
  val topLeft = Offset(
    x = center.x - runeWidth / 2,
    y = center.y - runeHeight / 2,
  )

  rotate(
    degrees = angle,
    pivot = center,
  ) {
    drawImage(
      image = runeImage,
      alpha = alpha,
      dstOffset = IntOffset(
        x = topLeft.x.roundToInt(),
        y = topLeft.y.roundToInt(),
      ),
      dstSize = IntSize(
        width = runeWidth.roundToInt(),
        height = runeHeight.roundToInt(),
      ),
    )
  }
}