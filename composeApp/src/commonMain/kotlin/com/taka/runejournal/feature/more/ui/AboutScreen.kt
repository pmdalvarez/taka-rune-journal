package com.taka.runejournal.feature.more.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.about_title
import taka_rune_journal.composeapp.generated.resources.about_what_is_taka
import taka_rune_journal.composeapp.generated.resources.book_with_taka_symbol
import taka_rune_journal.composeapp.generated.resources.cloth_bag_with_runes
import taka_rune_journal.composeapp.generated.resources.open_book
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_intro
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_readings
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_readings_title
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_runes
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_runes_title

@Preview
@Composable
fun AboutScreen(
  onBackClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  TakaScaffold(
    modifier = modifier,
    topBar = {
      TakaTopBar(
        title = stringResource(Res.string.about_title),
        navigationIcon = TakaTopBarNavigationIcon.Back,
        onNavigationClick = onBackClick,
      )
    },
  ) { contentModifier ->
    Column(
      modifier = contentModifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(TakaContentSpacing),
    ) {
      Text(
        modifier = Modifier
          .align(Alignment.Start),
        text = stringResource(Res.string.about_what_is_taka),
        style = MaterialTheme.typography.titleMedium
      )
      Text(
        modifier = Modifier
          .fillMaxWidth(),
        text = stringResource(Res.string.timeline_welcome_slide_intro),
        style = MaterialTheme.typography.bodyMedium,
      )
      Text(
        modifier = Modifier
          .align(Alignment.Start),
        text = stringResource(Res.string.timeline_welcome_slide_runes_title),
        style = MaterialTheme.typography.titleMedium
      )
      Text(
        modifier = Modifier
          .fillMaxWidth(),
        text = stringResource(Res.string.timeline_welcome_slide_runes),
        style = MaterialTheme.typography.bodyMedium,
      )
      Text(
        modifier = Modifier
          .align(Alignment.Start),
        text = stringResource(Res.string.timeline_welcome_slide_readings_title),
        style = MaterialTheme.typography.titleMedium
      )
      Text(
        modifier = Modifier
          .fillMaxWidth(),
        text = stringResource(Res.string.timeline_welcome_slide_readings),
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}