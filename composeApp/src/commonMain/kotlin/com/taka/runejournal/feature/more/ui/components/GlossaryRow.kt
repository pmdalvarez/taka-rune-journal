package com.taka.runejournal.feature.more.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.core.domain.model.RuneOrientation
import com.taka.runejournal.core.ui.components.TakaCard
import com.taka.runejournal.core.ui.drawable
import com.taka.runejournal.core.ui.generalKeywords
import com.taka.runejournal.core.ui.theme.TakaSpaceMd
import com.taka.runejournal.core.ui.toDotSeparatedKeywords
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Preview
@Composable
fun GlossaryRow(
  rune: Rune = Rune.FEHU,
  onRuneClick: (Rune) -> Unit = {},
  modifier: Modifier = Modifier,
) {
  TakaCard(
    modifier = modifier
      .clickable { onRuneClick(rune) }
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.Top,
    ) {
      Image(
        painter = painterResource(rune.drawable()),
        contentDescription = rune.displayName,
        modifier = Modifier.size(width = 72.dp, height = 108.dp)
      )

      Spacer(modifier = Modifier.width(TakaSpaceMd))

      Column(
        modifier = Modifier.weight(1f),
      ) {
        Text(
          text = rune.displayName,
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.onSurface,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
        Text(
          text = stringResource(DrawnRune(rune, RuneOrientation.UPRIGHT).generalKeywords()).toDotSeparatedKeywords(),
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }
  }
}