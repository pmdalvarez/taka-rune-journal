package com.taka.runejournal.feature.reading.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.core.ui.ImmersiveModeEffect
import com.taka.runejournal.core.ui.ShakeDetectorEffect
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.core.ui.theme.TakaScreenPadding
import com.taka.runejournal.feature.reading.domain.model.RuneVisualState
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.cloth_background
import taka_rune_journal.composeapp.generated.resources.cloth_background_zoomed
import taka_rune_journal.composeapp.generated.resources.reading_draw_topbar_title
import kotlin.random.Random

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
  var canvasSize by remember { mutableStateOf(Size.Zero) }
  val clothBackground = when (uiState.drawPhase) {
    DrawPhase.SHAKE -> imageResource(Res.drawable.cloth_background_zoomed)
    else -> imageResource(Res.drawable.cloth_background)
  }
  ImmersiveModeEffect(enabled = isShaking) // status bar hidden (immersive mode) when shaking phone
  ShakeDetectorEffect(
    onShakeImpulse = { direction, strength ->
println("XXXXXXXXX onShakeImpulse direction: $direction, strength: $strength")
      isShaking = true
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
    // ✅ Runs once on first composition
    LaunchedEffect(Unit) {
      val runeVisualStates: Map<Rune, RuneVisualState> = randomizeRuneVisualStates(
        maxWidth.value,
        maxHeight.value
      )
    }

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
    Box(
      modifier = Modifier
        .background(
          color = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f),
          shape = MaterialTheme.shapes.small,
        )
        .padding(horizontal = TakaScreenPadding, vertical = TakaScreenPadding),
    ) {
      Text("SHAKE THE PHONE TO MIX THE RUNES")
    }


  }
}

@Composable
private fun RuneCanvas(
  clothTexture: ImageBitmap,
  modifier: Modifier = Modifier,
) {
//  Canvas(
//    modifier = modifier,
//  ) {
//    drawRect(
//      brush = ShaderBrush(
//        ImageShader(
//          image = clothTexture,
//          tileModeX = TileMode.Repeated,
//          tileModeY = TileMode.Repeated,
//        )
//      ),
//      size = size,
//    )
//    // draw runes here
//  }
}


// To do randomize based on screen size
fun randomizeRuneVisualStates(width: Float, height: Float)
  = Rune.entries.associateWith {
    RuneVisualState(
      position = Offset(
        x = Random.nextFloat() * 100f * width,
        y = Random.nextFloat() * 100f * height
      ),
      depth = Random.nextFloat() ,
      angle = Random.nextFloat() * 360
    )
  }


