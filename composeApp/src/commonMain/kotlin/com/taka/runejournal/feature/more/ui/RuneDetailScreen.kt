package com.taka.runejournal.feature.more.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.core.domain.model.RuneOrientation
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.drawableVector
import com.taka.runejournal.core.ui.generalInterpretation
import com.taka.runejournal.core.ui.generalKeywords
import com.taka.runejournal.core.ui.origin
import com.taka.runejournal.core.ui.supplementalInterpretation
import com.taka.runejournal.core.ui.supplementalKeywords
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceSm
import com.taka.runejournal.core.ui.toDotSeparatedKeywords
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.glossary_title
import taka_rune_journal.composeapp.generated.resources.rune_display_name_reversed

@Preview
@Composable
fun RuneDetailScreen(
  rune: Rune = Rune.FEHU,
  onBackClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  TakaScaffold(
    modifier = modifier,
    topBar = {
      TakaTopBar(
        title = stringResource(Res.string.glossary_title),
        navigationIcon = TakaTopBarNavigationIcon.Back,
        onNavigationClick = onBackClick,
      )
    },
  ) { contentModifier ->
    Column(
      modifier = contentModifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.Top,
    ) {
      RuneOrientationDetail(rune, RuneOrientation.UPRIGHT)
      if (rune.isReversible) {
        Spacer(modifier = Modifier.padding(top = TakaSectionSpacing))
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        RuneOrientationDetail(rune, RuneOrientation.REVERSED)
      }
    }
  }
}

@Composable
fun RuneOrientationDetail(
  rune: Rune,
  orientation: RuneOrientation
) {
  val drawnRune = DrawnRune(rune, orientation)
  val displayName = if (drawnRune.orientation == RuneOrientation.REVERSED) {
    stringResource(Res.string.rune_display_name_reversed, drawnRune.rune.displayName)
  } else {
    drawnRune.rune.displayName
  }
  val iconModifer = Modifier
    .then(if (orientation == RuneOrientation.REVERSED) Modifier.rotate(180f) else Modifier)
  Spacer(modifier = Modifier.padding(top = TakaSectionSpacing))

  Icon(
    painter = painterResource(rune.drawableVector()),
    contentDescription = displayName,
    modifier = iconModifer
      .fillMaxWidth()
      .size(width = 96.dp, height = 144.dp),
    tint = MaterialTheme.colorScheme.onSurfaceVariant,
  )

  Text(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = TakaSpaceSm),
    text = displayName,
    style = MaterialTheme.typography.titleLarge,
    color = MaterialTheme.colorScheme.onSurface,
    textAlign = TextAlign.Center,
  )
  Text(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = TakaSectionSpacing),
    text = stringResource(drawnRune.generalKeywords()).toDotSeparatedKeywords(),
    style = MaterialTheme.typography.labelMedium,
    color = MaterialTheme.colorScheme.onSurfaceVariant,
    textAlign = TextAlign.Center,
  )

  if (drawnRune.orientation == RuneOrientation.UPRIGHT) {
    Text(
      modifier = Modifier
        .padding(bottom = TakaContentSpacing),
      text = stringResource(rune.origin()),
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }

  Text(
    text = stringResource(drawnRune.generalInterpretation()),
    style = MaterialTheme.typography.bodyMedium,
    color = MaterialTheme.colorScheme.onSurfaceVariant
  )

  for (topic in ReadingTopic.entries.filterNot { it == ReadingTopic.GENERAL }) {
    val supplementalKeywords =  drawnRune.supplementalKeywords(topic)
    val supplementalInterpretation = drawnRune.supplementalInterpretation(topic)
    if (supplementalKeywords != null || supplementalInterpretation != null) {
      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = TakaSectionSpacing),
        text = stringResource(topic.title),
        style = MaterialTheme.typography.titleMedium
      )
      supplementalKeywords?.let {
        Text(
          text = stringResource(it).toDotSeparatedKeywords(),
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
      supplementalInterpretation?.let {
        Text(
          modifier = Modifier.padding(top = TakaContentSpacing),
          text = stringResource(it),
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}
