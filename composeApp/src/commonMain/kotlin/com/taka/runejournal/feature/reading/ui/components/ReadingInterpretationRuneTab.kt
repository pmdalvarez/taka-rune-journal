package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.RuneOrientation
import com.taka.runejournal.core.ui.components.TakaCard
import com.taka.runejournal.core.ui.drawable
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.feature.reading.ui.origin
import com.taka.runejournal.feature.reading.ui.toDotSeparatedKeywords
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.rune_display_name_reversed

@Composable
fun ReadingInterpretationRuneTab(
  drawnRune: DrawnRune,
  interpretation: StringResource,
  supplementalInterpretation: StringResource?,
  keywords: StringResource,
  supplementalKeywords: StringResource?,
  positionDescription: StringResource?,
) {
  val keywords = supplementalKeywords?.let {
    (stringResource(supplementalKeywords) +", " + stringResource(keywords)).toDotSeparatedKeywords()
  } ?: stringResource(keywords).toDotSeparatedKeywords()
  val fullInterpretation = listOfNotNull(
    positionDescription?.let { stringResource(it) },
    stringResource(drawnRune.rune.origin()),
    stringResource(interpretation),
    supplementalInterpretation?.let { stringResource(it) }
  ).joinToString("\n\n")

  TakaCard(
    modifier = Modifier
      .fillMaxHeight()
  ) {
    val drawnRuneName = if (drawnRune.orientation == RuneOrientation.REVERSED) {
      stringResource(Res.string.rune_display_name_reversed, drawnRune.rune.displayName)
    } else {
      drawnRune.rune.displayName
    }
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = drawnRuneName,
      style = MaterialTheme.typography.headlineSmall,
      textAlign = TextAlign.Center,
    )
    val imageModifer = Modifier
      .size(width = 96.dp, height = 144.dp)
      .padding(top = TakaContentSpacing)
      .align(Alignment.CenterHorizontally)
      .then(if (drawnRune.orientation == RuneOrientation.REVERSED) Modifier.rotate(180f) else Modifier)
    Image(
      painter = painterResource(drawnRune.rune.drawable()),
      contentDescription =drawnRuneName,
      contentScale = ContentScale.Fit,
      modifier = imageModifer
    )
    Text(
      modifier = Modifier.fillMaxWidth().padding(top = TakaContentSpacing),
      text = keywords,
      style = MaterialTheme.typography.bodyMedium,
      textAlign = TextAlign.Center,
    )
    Text(
      modifier = Modifier.fillMaxWidth().padding(top = TakaContentSpacing),
      text = fullInterpretation,
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}