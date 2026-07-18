package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.theme.TakaCardPadding

@Composable
fun TakaOverlayCard(
  modifier: Modifier = Modifier,
  verticalArrangement: Arrangement.Vertical = Arrangement.Top,
  shape: Shape = MaterialTheme.shapes.small,
  content: @Composable ColumnScope.() -> Unit,
) {
  Card(
    modifier = modifier,
    shape = shape,
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.72f),
      contentColor = MaterialTheme.colorScheme.onSurface
    ),
    border = BorderStroke(
      width =  2.dp,
      color = MaterialTheme.colorScheme.onSurface
    )
  ) {
    Column(
      modifier = Modifier.padding(TakaCardPadding),
      verticalArrangement = verticalArrangement,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      content()
    }
  }
}