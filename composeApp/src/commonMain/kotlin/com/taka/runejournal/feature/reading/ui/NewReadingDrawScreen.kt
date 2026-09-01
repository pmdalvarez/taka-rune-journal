package com.taka.runejournal.feature.reading.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.RuneOrientation
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
import com.taka.runejournal.feature.reading.ui.components.ReadingInterpretationContextHeader
import com.taka.runejournal.feature.reading.ui.components.RuneCanvas
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.button_go_to_reading
import taka_rune_journal.composeapp.generated.resources.button_reveal_runes
import taka_rune_journal.composeapp.generated.resources.cloth_background
import taka_rune_journal.composeapp.generated.resources.cloth_background_zoomed
import taka_rune_journal.composeapp.generated.resources.reading_draw_instructions_drag
import taka_rune_journal.composeapp.generated.resources.reading_draw_instructions_shake
import taka_rune_journal.composeapp.generated.resources.reading_draw_instructions_tap
import taka_rune_journal.composeapp.generated.resources.reading_draw_instructions_title
import taka_rune_journal.composeapp.generated.resources.reading_draw_selected_all_runes
import taka_rune_journal.composeapp.generated.resources.reading_draw_selected_rune_count
import taka_rune_journal.composeapp.generated.resources.reading_draw_your_runes
import taka_rune_journal.composeapp.generated.resources.reading_draw_youve_drawn_rune
import taka_rune_journal.composeapp.generated.resources.reading_draw_youve_drawn_rune_reversed
import taka_rune_journal.composeapp.generated.resources.reading_type_general
import taka_rune_journal.composeapp.generated.resources.rune_display_name_reversed

@Composable
fun NewReadingDrawScreen(
  viewModel: NewReadingViewModel,
  onBackClick: () -> Unit,
  onNavigateToReadingInterpretation: (id: Long) -> Unit,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
      if (drawState == DrawState.Choose.Dragging || drawState is DrawState.Reveal) return@ShakeDetectorEffect
      runeCanvasState = runeCanvasState.applyShakeImpulse(direction, strength)
      runeHapticFeedback.playStoneClink(strength)
    },
    onShakingChanged = { isShaking ->
      if (drawState is DrawState.Reveal) return@ShakeDetectorEffect
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
      durationMillis = RuneCanvasState.RUNE_REVEAL_CENTERING_RUNES_ANIMATION_MILLIS.toInt(), // Adjust duration as needed
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
    // Runs once on first composition to set canvas size to device screen size
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
          drawState = DrawState.Choose.Dragging
          showInstructionalOverLay = false
          runeCanvasState = runeCanvasState.startDraggingRune(position)
        },
        onRuneDrag = { position ->
          runeCanvasState = runeCanvasState.dragRuneToPosition(position)
        },
        onRuneDragStop = {
          drawState = DrawState.Choose.Idle
          runeCanvasState = runeCanvasState.stopDraggingRune()
        },
        onRuneTap = { position ->
          showInstructionalOverLay = false
          runeCanvasState = runeCanvasState.toggleRuneSelectionAtPosition(position)
        }
      )
    }

    // Instructional overlay appears at the beginning and disappears upon shake, drag or tap
    AnimatedVisibility(
      visible = showInstructionalOverLay,
      exit = fadeOut(tween(750)) // 1-second fade
    ) {
      val maxOverlayWidth = (maxWidth.value * 0.7).dp
      InstructionalOverlay(modifier = Modifier.widthIn(max = maxOverlayWidth))
    }

    LaunchedEffect(drawState) {
      // Once drawState is in Reveal, this transitions the reveal phase through the 3 different animations
      when (drawState) {
        is DrawState.Reveal.CenteringRunes -> {
          delay(RuneCanvasState.RUNE_REVEAL_CENTERING_RUNES_ANIMATION_MILLIS)
          drawState = DrawState.Reveal.UnveilingGlyphs
        }

        is DrawState.Reveal.UnveilingGlyphs -> {
          delay(RuneCanvasState.RUNE_REVEAL_UNVEILING_GLYPHS_ANIMATION_MILLIS)
          drawState = DrawState.Reveal.CompletingAnimations
        }

        else -> {} // Do nothing
      }
    }

    if (drawState is DrawState.Choose.Idle && runeCanvasState.selectedRunes.size > 0) {
      SelectedRuneCountOverlay(
        selectedRuneCount = runeCanvasState.selectedRunes.size,
        requiredRuneCount = uiState.spread?.runeCount ?: 0,
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .navigationBarsPadding()
          .padding(bottom = TakaScreenPadding),
        onRevealRuneClick = {
          drawState = DrawState.Reveal.CenteringRunes
          runeCanvasState = runeCanvasState.centerSelectedRunes()
        }
      )
    }

    if (drawState is DrawState.Reveal.CompletingAnimations) {
      TakaScaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        snackbarHost = { TakaSnackbarHost(hostState = snackbarHostState) },
        topBar = {
          TakaTopBar(
            title = stringResource(uiState.topic?.readingType() ?: Res.string.reading_type_general),
            navigationIcon = TakaTopBarNavigationIcon.Back,
            onNavigationClick = onBackClick,
          )
        },
      ) { contentModifier ->
        if (!uiState.question.isNullOrBlank()) {
          Box(
            modifier = contentModifier
              .fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
          ) {
            ReadingInterpretationContextHeader(
              question = uiState.question
            )
          }
        }
        BoxWithConstraints(
          modifier = Modifier
            .fillMaxSize(),
          contentAlignment = Alignment.Center,
        ) {
          // Calculate the vertical offset just below the revealed runes
          val verticalOffsetDp = with(density) {
            maxHeight / 2 + (runeCanvasState.runeHeight * RuneCanvasState.ZOOM_REVEAL).toDp() / 2
          }
          RevealedRunesOverlay(
            modifier = Modifier
              .wrapContentSize()
              .align(Alignment.TopCenter)
              .offset(y = verticalOffsetDp),
            onGoToReadingClick = {
              viewModel.saveAndNavigateToReading(runeCanvasState.drawnRunes())
            },
            drawnRunes = runeCanvasState.drawnRunes()
          )
        }
      }
    }
  }
}

@Preview
@Composable
private fun InstructionalOverlay(modifier:Modifier = Modifier.widthIn(max = 500.dp)) {
  Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    TakaOverlayCard(modifier = modifier, alpha = 0.82f) {
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

@Preview
@Composable
private fun SelectedRuneCountOverlay(
  selectedRuneCount: Int = 1,
  requiredRuneCount: Int = 3,
  modifier: Modifier = Modifier,
  onRevealRuneClick: () -> Unit = {},
) {
  val alpha = (0.6f + (selectedRuneCount / requiredRuneCount.toFloat()) * 0.3f) // gradually gets less transparent as more runes selected
  TakaOverlayCard(modifier = modifier, alpha = alpha) {
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

@Composable
private fun RevealedRunesOverlay(
  modifier: Modifier = Modifier,
  onGoToReadingClick: () -> Unit = {},
  drawnRunes: List<DrawnRune>,
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    when (drawnRunes.size) {
      1 -> {
        val drawnRuneText = drawnRunes.first().let { drawnRune ->
          if (drawnRune.orientation == RuneOrientation.REVERSED) {
            stringResource(Res.string.reading_draw_youve_drawn_rune_reversed, drawnRune.rune.displayName)
          } else {
            stringResource(Res.string.reading_draw_youve_drawn_rune, drawnRune.rune.displayName)
          }
        }
        Text(
          text = drawnRuneText,
          style = MaterialTheme.typography.titleMedium
        )
      }
      else -> {
        Text(
          text = stringResource(Res.string.reading_draw_your_runes),
          style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(TakaContentSpacing))
        val drawnRunesText = buildString {
          drawnRunes.forEachIndexed { index, drawnRune ->
            if (index > 0) append(" · ")
            if (drawnRune.orientation == RuneOrientation.REVERSED) {
              append(stringResource(Res.string.rune_display_name_reversed, drawnRune.rune.displayName))
            } else {
              append(drawnRune.rune.displayName)
            }
          }
        }
        Text(
          text = drawnRunesText,
          style = MaterialTheme.typography.bodyMedium
        )
      }
    }

    TakaButton(
      onClick = onGoToReadingClick,
      modifier = Modifier
        .padding(top = TakaContentSpacing)
    ) {
      Text(stringResource(Res.string.button_go_to_reading))
    }
  }
}