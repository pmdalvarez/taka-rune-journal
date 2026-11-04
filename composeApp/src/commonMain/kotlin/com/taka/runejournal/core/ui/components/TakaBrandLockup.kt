package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taka.runejournal.core.ui.theme.TakaWordmarkVerticalOffset
import com.taka.runejournal.core.ui.theme.wordmarkFontFamily
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.app_name
import taka_rune_journal.composeapp.generated.resources.ic_topbar_icon

/**
 * The Taka brand lockup: the symbol with the TAKA wordmark, both in the brand colour.
 * [scale] 1 is the top bar size; everything scales together so the proportions stay the same.
 * [label] is an optional line above TAKA (e.g. "Welcome to"), starting where TAKA starts.
 */
@Composable
fun TakaBrandLockup(
  modifier: Modifier = Modifier,
  scale: Float = 1f,
  label: String? = null,
) {
  val brandColor = MaterialTheme.colorScheme.primary
  Box(
    modifier = modifier.height(48.dp * scale),
    contentAlignment = Alignment.CenterStart,
  ) {
    Image(
      painter = painterResource(Res.drawable.ic_topbar_icon),
      contentDescription = null,
      modifier = Modifier.size(36.dp * scale),
      contentScale = ContentScale.Fit,
      colorFilter = ColorFilter.tint(brandColor),
    )
    label?.let {
      Text(
        text = it,
        modifier = Modifier
          .align(Alignment.TopStart)
          .padding(start = 30.dp * scale),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
    Text(
      text = stringResource(Res.string.app_name).uppercase(),
      modifier = Modifier
        .padding(start = 30.dp * scale)
        .offset(y = TakaWordmarkVerticalOffset * scale), // centres the capitals on the icon
      style = MaterialTheme.typography.titleMedium.let { style ->
        style.copy(
          fontFamily = wordmarkFontFamily(),
          fontWeight = FontWeight.Bold,
          fontSize = style.fontSize * scale,
          letterSpacing = 2.5.sp * scale,
        )
      },
      color = brandColor,
    )
  }
}
