package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.theme.TakaCardPadding

@Composable
fun TakaSelectableCard(
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  verticalArrangement: Arrangement.Vertical = Arrangement.Top,
  shape: Shape = MaterialTheme.shapes.small,
  content: @Composable ColumnScope.() -> Unit,
) {
  Card(
    onClick = onClick,
    modifier = modifier.fillMaxWidth().fillMaxHeight(),
    shape = shape,
    colors = CardDefaults.cardColors(
      containerColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
      } else {
        MaterialTheme.colorScheme.surfaceContainer
      },
      contentColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
      } else {
        MaterialTheme.colorScheme.onSurface
      },
    ),
    border = BorderStroke(
      width = if (isSelected) 2.dp else 1.dp,
      color = if (isSelected) {
        MaterialTheme.colorScheme.primary
      } else {
        MaterialTheme.colorScheme.outline
      }
    )
  ) {
    Column(
      modifier = Modifier.fillMaxWidth().padding(TakaCardPadding),
      verticalArrangement = verticalArrangement,
    ) {
      content()
    }
  }
}