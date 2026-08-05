package com.taka.runejournal.feature.reading.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.ImmersiveModeEffect
import com.taka.runejournal.core.ui.ShakeDetectorEffect
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaOverlayCard
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaScreenPadding
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.button_reveal_runes
import taka_rune_journal.composeapp.generated.resources.cloth_background
import taka_rune_journal.composeapp.generated.resources.cloth_background_zoomed
import taka_rune_journal.composeapp.generated.resources.reading_draw_instructions_drag
import taka_rune_journal.composeapp.generated.resources.reading_draw_instructions_shake
import taka_rune_journal.composeapp.generated.resources.reading_draw_instructions_tap
import taka_rune_journal.composeapp.generated.resources.reading_draw_instructions_title
import taka_rune_journal.composeapp.generated.resources.reading_draw_selected_all_runes
import taka_rune_journal.composeapp.generated.resources.reading_draw_selected_rune_count
import taka_rune_journal.composeapp.generated.resources.reading_draw_topbar_title
import taka_rune_journal.composeapp.generated.resources.rune_empty
import taka_rune_journal.composeapp.generated.resources.rune_empty_brown
import taka_rune_journal.composeapp.generated.resources.rune_empty_glowing
import taka_rune_journal.composeapp.generated.resources.rune_empty_half_glowing
import kotlin.math.roundToInt

@Composable
fun NewReadingDrawScreen(
  viewModel: NewReadingViewModel,
  onBackClick: () -> Unit,
  onNavigateToReadingInterpretation: (id: Long) -> Unit,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  val snackbarHostState = remember { SnackbarHostState() }
  var showInstructionalOverLay by remember { mutableStateOf(true) }
  var drawState by remember { mutableStateOf<DrawState>(DrawState.Choose.Idle) }
  val clothBackground = if (drawState == DrawState.Choose.Shaking) {
      imageResource(Res.drawable.cloth_background_zoomed)
    } else {
      imageResource(Res.drawable.cloth_background)
    }
  var runeCanvasState by remember { mutableStateOf(RuneCanvasState(0f, 0f, 1)) }
  val runeHapticFeedback = rememberRuneHapticFeedback()

  ImmersiveModeEffect(enabled = drawState is DrawState.Choose) // status bar hidden (immersive mode) when shaking phone
  ShakeDetectorEffect(
    onShakeImpulse = { direction, strength ->
println("XXXXXXXXXXXXXXXXXXXXXX direction: $direction, strength: $strength")
      if (drawState == DrawState.Choose.Dragging) return@ShakeDetectorEffect
      runeCanvasState = runeCanvasState.applyShakeImpulse(direction, strength)
      runeHapticFeedback.playStoneClink(strength)
    },
    onShakingChanged = { isShaking ->
println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX onShakingChanged isShaking: $isShaking")
      if (isShaking) {
        drawState = DrawState.Choose.Shaking
        showInstructionalOverLay = false
      } else {
        drawState = DrawState.Choose.Idle
      }
    }
  )
  LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
      when (event) {
        is UiEvent.ShowError -> snackbarHostState.showErrorSnackbar(message = getString(event.messageRes))
        is UiEvent.NavigateToItem -> onNavigateToReadingInterpretation(event.itemId)
        else -> {} // No other events expected
      }
    }
  }

  val clothBackgroundAlpha by animateFloatAsState(
    targetValue = if (drawState is DrawState.Choose) 1f else 0f,
    animationSpec = tween(
      durationMillis = RuneCanvasState.RUNE_REVEAL_ANIMATION_MILLIS.toInt(), // Adjust duration as needed
      easing = LinearOutSlowInEasing,
    ),
    label = "Background Alpha"
  )

  BoxWithConstraints(
    modifier = Modifier.fillMaxSize()
      .background(
        brush = ShaderBrush(
          ImageShader(
            image = clothBackground,
            tileModeX = TileMode.Repeated,
            tileModeY = TileMode.Repeated,
          )
        ),
        alpha = clothBackgroundAlpha
      ),
      contentAlignment = Alignment.Center
  ) {
    val density = LocalDensity.current
    // ✅ Runs once on first composition
    LaunchedEffect(Unit) {
      val canvasWidthPx = with(density) { maxWidth.toPx() }
      val canvasHeightPx = with(density) { maxHeight.toPx() }
      runeCanvasState = RuneCanvasState(canvasWidthPx, canvasHeightPx, uiState.spread?.runeCount ?: 1)
    }

    if (runeCanvasState.runeVisualStates.isNotEmpty()) {
      RuneCanvas(
        modifier = Modifier.matchParentSize(),
        runeCanvasState = runeCanvasState,
        drawState = drawState,
        onRuneDragStart = { position ->
          showInstructionalOverLay = false
          runeCanvasState = runeCanvasState.startDraggingRune(position)
        },
        onRuneDrag = { position ->
          runeCanvasState = runeCanvasState.dragRuneToPosition(position)
        },
        onRuneDragStop = {
          runeCanvasState = runeCanvasState.stopDraggingRune()
        },
        onRuneTap = { position ->
          showInstructionalOverLay = false
          runeCanvasState = runeCanvasState.toggleRuneSelectionAtPosition(position)
        }
      )
    }

    // Instructional overlay appears at the beginning and then fades away forever. Or disappears immediately upon shake, drag or tap
    LaunchedEffect(Unit) {
      delay(5000) // Wait few seconds
      showInstructionalOverLay = false // Trigger fade out
    }
    AnimatedVisibility(
      visible = showInstructionalOverLay,
      exit = fadeOut(tween(750)) // 1-second fade
    ) {
      val maxOverlayWidth = (maxWidth.value * 0.7).dp
      IntructionalOverlay(modifier = Modifier.widthIn(max = maxOverlayWidth))
    }

    LaunchedEffect(drawState) {
      // Once drawState is in Reveal, this transitions the reveal phase through the 3 different animations
      when (drawState) {
        is DrawState.Reveal.CenteringRunes -> {
          delay(RuneCanvasState.RUNE_REVEAL_ANIMATION_MILLIS)
          drawState = DrawState.Reveal.UnveilingGlyphs
        }
        is DrawState.Reveal.UnveilingGlyphs -> {
          delay(RuneCanvasState.RUNE_REVEAL_ANIMATION_MILLIS)
          drawState = DrawState.Reveal.CompletingAnimations
        }
        else -> {} // Do nothing
      }
    }
    uiState.spread?.runeCount?.let { runeCount ->
      if (runeCanvasState.selectedRunes.size > 0) {
        SelectedRuneCountOverlay(
          selectedRuneCount = runeCanvasState.selectedRunes.size,
          requiredRuneCount = runeCount,
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .navigationBarsPadding()
            .padding(bottom = TakaScreenPadding),
          onRevealRuneClick = {
            drawState = DrawState.Reveal.CenteringRunes
          }
        )
      }
    }
  }

  if (drawState is DrawState.Reveal) {
    TakaScaffold(
      modifier = modifier,
      containerColor = Color.Transparent,
      snackbarHost = { TakaSnackbarHost(hostState = snackbarHostState) },
      topBar = {
        TakaTopBar(
          title = stringResource(Res.string.reading_draw_topbar_title),
          navigationIcon = TakaTopBarNavigationIcon.Back,
          onNavigationClick = onBackClick,
        )
      },
    ) { contentPadding ->
      // nothing here yet - maybe in reveal phase there is a button go to reading and runes are shown
    }
  }
}

@Preview
@Composable
private fun IntructionalOverlay(modifier:Modifier = Modifier.widthIn(max = 500.dp)) {
  Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    TakaOverlayCard(modifier = modifier) {
      Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = stringResource(Res.string.reading_draw_instructions_title),
        style = MaterialTheme.typography.titleMedium
      )
      Spacer(modifier = Modifier.height(TakaSectionSpacing))
      Text(
        modifier = Modifier.align(Alignment.Start),
        text = stringResource(Res.string.reading_draw_instructions_shake),
        style = MaterialTheme.typography.bodyLarge
      )
      Text(
        modifier = Modifier.align(Alignment.Start),
        text = stringResource(Res.string.reading_draw_instructions_drag),
        style = MaterialTheme.typography.bodyLarge
      )
      Text(
        modifier = Modifier.align(Alignment.Start),
        text = stringResource(Res.string.reading_draw_instructions_tap),
        style = MaterialTheme.typography.bodyLarge
      )
    }
  }
}

@Composable
private fun RuneCanvas(
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
  val emptyRuneImageBrown = imageResource(Res.drawable.rune_empty_brown)
  val runeImageDrawStates = buildList {
      runeCanvasState.runeVisualStates
        .entries
        .sortedBy { it.value.depth }
        .forEach { (rune, visualState) ->
          key(rune) {
            val animatedCenter by animateOffsetAsState(
              targetValue = visualState.center,
              animationSpec = tween(
                durationMillis = RuneCanvasState.IMPULSE_INTERVAL_MILLIS.toInt(),
                easing = LinearOutSlowInEasing,
              ),
              label = "Rune ${rune.name} Center",
            )
            val animatedAngle by animateFloatAsState(
              targetValue = visualState.angle,
              animationSpec = tween(
                durationMillis = RuneCanvasState.IMPULSE_INTERVAL_MILLIS.toInt(),
                easing = LinearOutSlowInEasing,
              ),
              label = "Rune ${rune.name} Angle",
            )
            val isDraggedRune = runeCanvasState.draggedRuneState?.rune == rune
            val isSelected = runeCanvasState.isSelected(rune)
            val emptyRuneProgress by animateFloatAsState(
              targetValue = if (drawState is DrawState.Choose && !isSelected) 1f else 0f,
              animationSpec = tween(
                durationMillis = RuneCanvasState.RUNE_SELECTION_ANIMATION_MILLIS.toInt(),
                easing = LinearOutSlowInEasing,
              ),
              label = "Selected Rune ${rune.name} fade in animation",
            )
            val glowingEmptyRuneProgress by animateFloatAsState(
              targetValue = when (drawState) {
                is DrawState.Choose -> 1 - emptyRuneProgress
                is DrawState.Reveal.CenteringRunes -> if (isSelected) 1f else 0f
                is DrawState.Reveal.UnveilingGlyphs -> 0f
                is DrawState.Reveal.CompletingAnimations -> 0f
              },
              animationSpec = tween(
                durationMillis = RuneCanvasState.RUNE_SELECTION_ANIMATION_MILLIS.toInt(),
                easing = LinearOutSlowInEasing,
              ),
              label = "Selected Rune ${rune.name} fade in animation",
            )
            val glowingGlowingGlyphRuneProgress by animateFloatAsState(
              targetValue = if (drawState is DrawState.Reveal.UnveilingGlyphs && isSelected) 1f else 0f,
              animationSpec = tween(
                durationMillis = RuneCanvasState.RUNE_REVEAL_ANIMATION_MILLIS.toInt(),
                easing = LinearOutSlowInEasing,
              ),
              label = "Selected Rune ${rune.name} fade in animation",
            )
            val glyphRuneProgress by animateFloatAsState(
              targetValue = if (drawState is DrawState.Reveal.CompletingAnimations && isSelected) 1f else 0f,
              animationSpec = tween(
                durationMillis = RuneCanvasState.RUNE_REVEAL_ANIMATION_MILLIS.toInt(),
                easing = LinearOutSlowInEasing,
              ),
              label = "Selected Rune ${rune.name} fade in animation",
            )
            val drawCenter = if (isDraggedRune) visualState.center else animatedCenter
            val drawAngle = if (isDraggedRune) visualState.angle else animatedAngle

            // Unselected Rune
            add(RuneImageDrawState(image = emptyRuneImage, center =  drawCenter, angle = drawAngle, alpha = emptyRuneProgress))
            // Selected Rune - dragged rune cannot be in selected state
            if (!isDraggedRune) { add(RuneImageDrawState(image = emptyRuneImageGlowing, center =  drawCenter, angle = drawAngle, alpha = glowingEmptyRuneProgress)) }
            // Rune being unveiled - has glowing glyph
            add(RuneImageDrawState(image = emptyRuneImageHalfGlowing, center =  drawCenter, angle = drawAngle, alpha = glowingGlowingGlyphRuneProgress))
            // Rune fully unveiled- has non-glowing glyph
            add(RuneImageDrawState(image = emptyRuneImageBrown, center =  drawCenter, angle = drawAngle, alpha = glyphRuneProgress))
          }
        }
    }
  val zoom by animateFloatAsState(
    targetValue = when {
      drawState is DrawState.Choose.Shaking || drawState is DrawState.Reveal -> 1.5f
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

@Preview
@Composable
private fun SelectedRuneCountOverlay(
  selectedRuneCount: Int = 1,
  requiredRuneCount: Int = 3,
  modifier: Modifier = Modifier,
  onRevealRuneClick: () -> Unit = {},
) {
  TakaOverlayCard(modifier = modifier) {
    val selectedCountString = if (selectedRuneCount == requiredRuneCount) {
      stringResource(Res.string.reading_draw_selected_all_runes)
    } else {
      stringResource(Res.string.reading_draw_selected_rune_count, selectedRuneCount, requiredRuneCount)
    }
    Text(
      text = selectedCountString,
      style = MaterialTheme.typography.labelMedium
    )
    TakaButton(
      onClick = onRevealRuneClick,
      enabled = selectedRuneCount == requiredRuneCount,
      modifier = Modifier
        .padding(top = TakaContentSpacing)
        .navigationBarsPadding()
    ) {
      Text(stringResource(Res.string.button_reveal_runes))
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