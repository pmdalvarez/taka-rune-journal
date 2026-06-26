package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.theme.TakaScreenPadding


@Composable
fun TakaScaffold(
  modifier: Modifier = Modifier,
  snackbarHost: @Composable () -> Unit = {},
  contentPadding: PaddingValues = PaddingValues(
    start = TakaScreenPadding,
    end = TakaScreenPadding,
    bottom = TakaScreenPadding
  ),
  topBar: @Composable () -> Unit = {},
  content: @Composable (Modifier) -> Unit,
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    snackbarHost = snackbarHost,
    topBar = topBar,
  ) { innerPadding ->
    content(
      Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(contentPadding),
    )
  }
}