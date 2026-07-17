package com.taka.runejournal.feature.reading.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.imageResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.cloth_background_zoomed

@Composable
fun NewReadingDrawScreen(
  viewModel: NewReadingViewModel,
  onBackClick: () -> Unit,
  onNavigateToReadingInterpretation: (id: Long) -> Unit,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  val snackbarHostState = remember { SnackbarHostState() }
  var canvasSize by remember { mutableStateOf(Size.Zero) }
  val clothBackground = imageResource(Res.drawable.cloth_background_zoomed)

  LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
      when (event) {
        is UiEvent.ShowError -> { snackbarHostState.showErrorSnackbar(message = getString(event.messageRes)) }
        is UiEvent.NavigateToItem -> onNavigateToReadingInterpretation(event.itemId)
        else -> {} // No other events expected
      }
    }

    // TODO -> Restore This later
//    viewModel.randomizeRuneVisualStates(
//      maxWidth.value,
//      maxHeight.value
//    )
  }

  Box(
    modifier = modifier.fillMaxSize()
      .background(
        brush = ShaderBrush(
          ImageShader(
            image = clothBackground,
            tileModeX = TileMode.Repeated,
            tileModeY = TileMode.Repeated,
          )
        )
      )
    ,
  ) {
//    RuneCanvas(
//      modifier = Modifier.matchParentSize(),
//      clothTexture = clothTexture,
//    )

    TakaScaffold(
      modifier = Modifier.matchParentSize(),
      snackbarHost = { TakaSnackbarHost(hostState = snackbarHostState) },
      // transparent background
      topBar = {
        TakaTopBar(
          title = "Draw Runes",
          onNavigationClick = onBackClick,
          // transparent container if supported
        )
      },
    ) { contentPadding ->
      OverlayContent(
        uiState = uiState,
        modifier = contentPadding,
      )
    }
  }
  BoxWithConstraints(
    modifier = Modifier.fillMaxSize()
  ) {

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
    Text(
      text = "Draw Runes Screen. spread="+uiState.spread?.name+" topic="+uiState.topic?.name+" question="+uiState.question,
      style = MaterialTheme.typography.headlineMedium
    )
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

