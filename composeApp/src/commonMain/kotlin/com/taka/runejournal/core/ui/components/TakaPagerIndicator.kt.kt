package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.theme.TakaSpaceXs

@Composable
fun TakaPagerIndicator(
  pageCount: Int,
  currentPage: Int,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    repeat(pageCount) { index ->
      val isSelected = index == currentPage

      Box(
        modifier = Modifier
          .padding(horizontal = TakaSpaceXs)
          .size(if (isSelected) 8.dp else 6.dp)
          .clip(CircleShape)
          .background(
            if (isSelected) {
              MaterialTheme.colorScheme.primary
            } else {
              MaterialTheme.colorScheme.outlineVariant
            }
          )
      )
    }
  }
}