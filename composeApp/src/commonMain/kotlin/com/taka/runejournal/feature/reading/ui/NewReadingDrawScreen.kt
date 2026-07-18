package com.taka.runejournal.feature.reading.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.taka.runejournal.core.ui.ImmersiveModeEffect
import com.taka.runejournal.core.ui.ShakeDetectorEffect
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaOverlayCard
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.core.ui.theme.TakaScreenPadding
import com.taka.runejournal.feature.reading.domain.model.RuneVisualState
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.cloth_background
import taka_rune_journal.composeapp.generated.resources.cloth_background_zoomed
import taka_rune_journal.composeapp.generated.resources.reading_draw_shake_prompt
import taka_rune_journal.composeapp.generated.resources.reading_draw_topbar_title
import taka_rune_journal.composeapp.generated.resources.rune_empty
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
  var isShaking by remember { mutableStateOf(false) }
//  var canvasSize by remember { mutableStateOf(Size.Zero) }
  val clothBackground = when (uiState.drawPhase) {
    DrawPhase.SHAKE -> imageResource(Res.drawable.cloth_background_zoomed)
    else -> imageResource(Res.drawable.cloth_background)
  }
  val runePainter = painterResource(Res.drawable.rune_empty)
  var runeCanvasState by remember { mutableStateOf(RuneCanvasState(0f, 0f)) }

  ImmersiveModeEffect(enabled = isShaking) // status bar hidden (immersive mode) when shaking phone
  ShakeDetectorEffect(
    onShakeImpulse = { direction, strength ->
      isShaking = true
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
      )
  ) {
    val density = LocalDensity.current
    // ✅ Runs once on first composition
    LaunchedEffect(Unit) {
      val canvasWidthPx = with(density) { maxWidth.toPx() }
      val canvasHeightPx = with(density) { maxHeight.toPx() }
      runeCanvasState = RuneCanvasState(canvasWidthPx, canvasHeightPx)
    }

    RuneCanvas(
      modifier = Modifier.matchParentSize(),
      runePainter = runePainter,
      runeCanvasState = runeCanvasState,
      isZoomedIn = uiState.drawPhase == DrawPhase.SHAKE
    )

    if (!isShaking) {
      OverlayContent(
        uiState = uiState
      )
    }

  }


//    RuneCanvas(
//      modifier = Modifier.matchParentSize(),
//      clothTexture = clothTexture,
//    )

  TakaScaffold(
    modifier = modifier,
    containerColor = Color.Transparent,
    snackbarHost = { TakaSnackbarHost(hostState = snackbarHostState) },
    topBar = {
      if (!isShaking) { // only show top bar when not shaking phone
        TakaTopBar(
          title = stringResource(Res.string.reading_draw_topbar_title),
          navigationIcon = TakaTopBarNavigationIcon.Back,
          onNavigationClick = onBackClick,
        )
      }
    },
  ) { contentPadding ->
  }
}

@Composable
private fun OverlayContent(uiState: NewReadingUiState, modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    TakaOverlayCard(
      modifier = Modifier
        .padding(horizontal = TakaScreenPadding, vertical = TakaScreenPadding)
    ) {
      Text(
        text = stringResource(Res.string.reading_draw_shake_prompt),
        style = MaterialTheme.typography.titleMedium
      )
    }
  }
}

@Composable
private fun RuneCanvas(
  modifier: Modifier = Modifier,
  runePainter: Painter,
  runeCanvasState: RuneCanvasState,
  isZoomedIn: Boolean,
) {
  Canvas(
    modifier = modifier,
  ) {
    val zoom = if (isZoomedIn) 2f else 1f

    scale(
      scale = zoom,
      pivot = center,
    ) {
      runeCanvasState.runeVisualStates
        .entries
        .sortedBy { it.value.depth }
        .forEach { (_, visualState) ->
          drawRune(
            painter = runePainter,
            visualState = visualState,
            runeWidth = runeCanvasState.runeWidth,
            runeHeight = runeCanvasState.runeHeight,
          )
        }
    }
  }
}

private fun DrawScope.drawRune(
  painter: Painter,
  visualState: RuneVisualState,
  runeWidth: Float,
  runeHeight: Float,
) {
  val topLeft = Offset(
    x = visualState.center.x - runeWidth / 2,
    y = visualState.center.y - runeHeight / 2,
  )

  rotate(
    degrees = visualState.angle,
    pivot = visualState.center,
  ) {
    translate(
      left = topLeft.x,
      top = topLeft.y,
    ) {
      with(painter) {
        draw(
          size = Size(
            width = runeWidth,
            height = runeHeight,
          )
        )
      }
    }
  }
}