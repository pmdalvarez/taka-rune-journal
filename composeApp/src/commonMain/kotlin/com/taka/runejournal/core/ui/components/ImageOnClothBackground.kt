package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.imageResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.cloth_background

@Composable
fun ImageOnClothBackground(
  painter: Painter,
  contentDescription: String?,
  modifier: Modifier = Modifier,
  imageModifier: Modifier = Modifier,
  contentScale: ContentScale = ContentScale.Fit,
) {
  val clothImage = imageResource(Res.drawable.cloth_background)

  val clothBrush = remember(clothImage) {
    ShaderBrush(
      ImageShader(
        image = clothImage,
        tileModeX = TileMode.Repeated,
        tileModeY = TileMode.Repeated,
      )
    )
  }

  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center,
  ) {
    Canvas(
      modifier = Modifier
        .matchParentSize()
        .graphicsLayer {
          compositingStrategy = CompositingStrategy.Offscreen
        }
    ) {
      drawRect(
        brush = clothBrush,
      )

      drawRect(
        brush = Brush.horizontalGradient(
          colorStops = arrayOf(
            0f to Color.Transparent,
            0.05f to Color.Black,
            0.95f to Color.Black,
            1f to Color.Transparent,
          ),
        ),
        blendMode = BlendMode.DstIn,
      )

      drawRect(
        brush = Brush.verticalGradient(
          colorStops = arrayOf(
            0f to Color.Transparent,
            0.05f to Color.Black,
            0.95f to Color.Black,
            1f to Color.Transparent,
          ),
        ),
        blendMode = BlendMode.DstIn,
      )
    }

    Image(
      painter = painter,
      contentDescription = contentDescription,
      contentScale = contentScale,
      modifier = imageModifier,
    )
  }
}