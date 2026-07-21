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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.taka.runejournal.core.ui.components.TakaOverlayCard
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.cloth_background
import taka_rune_journal.composeapp.generated.resources.cloth_background_zoomed
import taka_rune_journal.composeapp.generated.resources.reading_draw_instructions_drag
import taka_rune_journal.composeapp.generated.resources.reading_draw_instructions_shake
import taka_rune_journal.composeapp.generated.resources.reading_draw_instructions_tap
import taka_rune_journal.composeapp.generated.resources.reading_draw_instructions_title
import taka_rune_journal.composeapp.generated.resources.reading_draw_topbar_title
import taka_rune_journal.composeapp.generated.resources.rune_empty
import taka_rune_journal.composeapp.generated.resources.rune_empty_glowing
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
  var isShaking by remember { mutableStateOf(false) }
  val clothBackground = when (uiState.drawPhase) {
    DrawPhase.CHOOSE -> imageResource(Res.drawable.cloth_background_zoomed)
    else -> imageResource(Res.drawable.cloth_background)
  }
  val emptyRuneImage = imageResource(Res.drawable.rune_empty)
  val emptyRuneImageGlowing = imageResource(Res.drawable.rune_empty_glowing)
  var runeCanvasState by remember { mutableStateOf(RuneCanvasState(0f, 0f, 1)) }
  val isDragging = runeCanvasState.draggedRuneState != null
  val runeHapticFeedback = rememberRuneHapticFeedback()

  ImmersiveModeEffect(enabled = isShaking) // status bar hidden (immersive mode) when shaking phone
  ShakeDetectorEffect(
    onShakeImpulse = { direction, strength ->
println("XXXXXXXXXXXXXXXXXXXXXX direction: $direction, strength: $strength")
      if (isDragging) return@ShakeDetectorEffect
      runeCanvasState = runeCanvasState.applyShakeImpulse(direction, strength)
      runeHapticFeedback.playStoneClink(strength)
    },
    onShakingChanged = {
      isShaking = it
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

  BoxWithConstraints(
    modifier = Modifier.fillMaxSize()
      .background(
        brush = ShaderBrush(
          ImageShader(
            image = clothBackground,
            tileModeX = TileMode.Repeated,
            tileModeY = TileMode.Repeated,
          )
        )
      ),
      contentAlignment = Alignment.Center
  ) {
    val density = LocalDensity.current
    // ✅ Runs once on first composition
    LaunchedEffect(Unit) {
      val canvasWidthPx = with(density) { maxWidth.toPx() }
      val canvasHeightPx = with(density) { maxHeight.toPx() }
      runeCanvasState = RuneCanvasState(canvasWidthPx, canvasHeightPx, uiState.spread?.runeCount ?: 1)
println("XXXXXXXXXXXXXXXXXXXXXX canvasWidthPx: $canvasWidthPx, canvasHeightPx: $canvasHeightPx, runeWidth: ${runeCanvasState.runeWidth}, runeHeight: ${runeCanvasState.runeHeight} requiredRuneCount:${uiState.spread?.runeCount ?: 1}")
    }

    if (runeCanvasState.runeVisualStates.isNotEmpty()) {
      RuneCanvas(
        modifier = Modifier.matchParentSize(),
        runeImage = emptyRuneImage,
        runeImageSelected = emptyRuneImageGlowing,
        runeCanvasState = runeCanvasState,
        isZoomedIn = isShaking,
        isGestureEnabled = !isShaking,
        onRuneDragStart = { position ->
          runeCanvasState = runeCanvasState.startDraggingRune(position)
        },
        onRuneDrag = { position ->
          runeCanvasState = runeCanvasState.dragRuneToPosition(position)
        },
        onRuneDragStop = {
  println("XXXXXXXXXXXXXXXXXXXXXX onRuneDragStop")
          runeCanvasState = runeCanvasState.stopDraggingRune()
        },
        onRuneTap = { position ->
          runeCanvasState = runeCanvasState.toggleRuneSelectionAtPosition(position)
        },
      )
    }

    // Instructional overlay appears at the beginning and then fades away forever. Or disappears immediately upon shake or drag
    LaunchedEffect(Unit) {
      delay(5000) // Wait few seconds
      showInstructionalOverLay = false // Trigger fade out
    }
    if (!isShaking && !isDragging) {
      AnimatedVisibility(
        visible = showInstructionalOverLay,
        exit = fadeOut(tween(1000)) // 1-second fade
      ) {
        val maxOverlayWidth = (maxWidth.value * 0.7).dp
        IntructionalOverlay(modifier = Modifier.widthIn(max = maxOverlayWidth))
      }
    }

  }

  if (uiState.drawPhase != DrawPhase.CHOOSE) {
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
  runeImage: ImageBitmap,
  runeImageSelected: ImageBitmap,
  runeCanvasState: RuneCanvasState,
  isZoomedIn: Boolean,
  isGestureEnabled: Boolean,
  onRuneDragStart: (touchPosition: Offset) -> Unit,
  onRuneDrag: (position: Offset) -> Unit,
  onRuneDragStop: () -> Unit,
  onRuneTap: (position: Offset) -> Unit,
) {
  val animatedRuneVisualStates = runeCanvasState.runeVisualStates
    .mapValues { (rune, visualState) ->
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

      val animatedAlpha by animateFloatAsState(
          targetValue = if (isSelected) 0f else 1f,
          animationSpec = tween(
            durationMillis = 450,
            easing = LinearOutSlowInEasing,
          ),
          label = "Rune ${rune.name} fade out animation",
        )

      // When dragging don't animate because animation not good at 345 -> 5 deg transition. Also not needed, already smooth without it
      visualState.copy(
        center = if (isDraggedRune) visualState.center else animatedCenter,
        angle = if (isDraggedRune) visualState.angle else animatedAngle,
        alpha = animatedAlpha,
      )
    }
  val zoom by animateFloatAsState(
    targetValue = if (isZoomedIn) 1.5f else 1f,
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
      animatedRuneVisualStates
        .entries
        .sortedBy { it.value.depth }
        .forEach { (rune, visualState) ->
println("XXXXXXXXXXXXXXXXXXXXXX drawing rune: $rune visualState: $visualState alpha: ${visualState.alpha}")
            if (visualState.alpha != 0f) { // skip drawing this if invisible
              drawRune(
                runeImage = runeImage,
                center = visualState.center,
                angle = visualState.angle,
                alpha = visualState.alpha,
                runeWidth = runeCanvasState.runeWidth,
                runeHeight = runeCanvasState.runeHeight
              )
            }
            if (visualState.alpha != 1f) { // skip drawing this if invisible
                drawRune(
                  runeImage = runeImageSelected,
                  center = visualState.center,
                  angle = visualState.angle,
                  alpha = 1 - visualState.alpha,
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
  alpha: Float = 1f, // For crossFading if rune is being selected or selected, represents alpha of unselected image
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