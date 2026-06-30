package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TakaTextAction(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Text(
    text = text,
    modifier = modifier.clickable(onClick = onClick),
    style = MaterialTheme.typography.labelLarge,
    color = MaterialTheme.colorScheme.primary,
  )
}