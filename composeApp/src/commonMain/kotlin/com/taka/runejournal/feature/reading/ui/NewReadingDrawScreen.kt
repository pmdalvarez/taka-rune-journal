package com.taka.runejournal.feature.reading.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.onGloballyPositioned

@Composable
fun NewReadingDrawScreen(
  viewModel: NewReadingViewModel,
  onBackClick: () -> Unit,
  onReadingSaved: (id: Long) -> Unit,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  var canvasSize by remember { mutableStateOf(Size.Zero) }

  BoxWithConstraints(
    modifier = Modifier.fillMaxSize()
  ) {
    // ✅ Runs once on first composition
    LaunchedEffect(Unit) {
      viewModel.randomizeRuneVisualStates(
        maxWidth.value,
        maxHeight.value
      )
    }

    Column(
      modifier = modifier
        .fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "Rune drawing screen spread="+uiState.spread?.name+" topic="+uiState.topic?.name+" question="+uiState.question,
        style = MaterialTheme.typography.headlineMedium
      )
    }
  }

}