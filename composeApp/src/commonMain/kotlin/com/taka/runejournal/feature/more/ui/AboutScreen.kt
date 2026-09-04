package com.taka.runejournal.feature.more.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.taka_description
import taka_rune_journal.composeapp.generated.resources.about_title

@Composable
fun AboutScreen(
  onBackClick: () -> Unit,
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
      modifier = contentModifier,
      verticalArrangement = Arrangement.spacedBy(TakaSectionSpacing),
    ) {
      Text(
        text = stringResource(Res.string.taka_description),
        style =  MaterialTheme.typography.bodyLarge,
      )
    }
  }
}