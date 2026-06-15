package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val TakaScreenPadding = 24.dp

@Composable
fun TakaScaffold(
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(TakaScreenPadding),
  topBar: @Composable () -> Unit = {},
  content: @Composable (Modifier) -> Unit,
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
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