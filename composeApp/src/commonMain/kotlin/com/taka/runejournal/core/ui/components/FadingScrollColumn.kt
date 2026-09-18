package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun FadingScrollColumn(
  modifier: Modifier = Modifier,
  content: @Composable ColumnScope.() -> Unit,
) {
  val scrollState = rememberScrollState()
  Column(
    modifier = modifier
      .fillMaxSize()
      .graphicsLayer {
        compositingStrategy = CompositingStrategy.Offscreen
      }
      .drawWithContent {
        drawContent()

        if (scrollState.canScrollForward) {
          drawRect(
            brush = Brush.verticalGradient(
              colorStops = arrayOf(
                0.82f to Color.Black,
                1f to Color.Transparent,
              )
            ),
            blendMode = BlendMode.DstIn,
          )
        }
      }
      .verticalScroll(scrollState),
    content = content,
  )
}