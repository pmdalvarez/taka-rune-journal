package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.theme.TakaSpaceXs

enum class ButtonStyle {
  Primary,
  Secondary,
  Tertiary
}

@Composable
fun TakaButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  style: ButtonStyle = ButtonStyle.Primary,
  enabled: Boolean = true,
  content: @Composable RowScope.() -> Unit
) = when (style) {
  ButtonStyle.Primary -> {
    Button(
      onClick = onClick,
      enabled = enabled,
      shape = MaterialTheme.shapes.small,
      colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
      ),
      modifier = modifier,
      content = content
    )
  }

  ButtonStyle.Secondary -> {
    OutlinedButton(
      onClick = onClick,
      enabled = enabled,
      shape = MaterialTheme.shapes.small,
      colors = ButtonDefaults.outlinedButtonColors(
        contentColor = MaterialTheme.colorScheme.primary,
        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
      ),
      border = BorderStroke(
        width = 1.dp,
        color = if (enabled) {
          MaterialTheme.colorScheme.primary
        } else {
          MaterialTheme.colorScheme.outlineVariant
        },
      ),
      modifier = modifier,
      content = content,
    )
  }

  ButtonStyle.Tertiary -> {
    TextButton(
      onClick = onClick,
      enabled = enabled,
      shape = MaterialTheme.shapes.small,
      colors = ButtonDefaults.textButtonColors(
        contentColor = MaterialTheme.colorScheme.primary,
        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
      ),
      modifier = modifier,
      content = content,
    )
  }
}