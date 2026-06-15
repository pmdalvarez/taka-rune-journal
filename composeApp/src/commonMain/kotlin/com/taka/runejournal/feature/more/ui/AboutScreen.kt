package com.taka.runejournal.feature.more.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.about_description
import taka_rune_journal.composeapp.generated.resources.about_title

@Composable
fun AboutScreen(
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      TakaTopBar(
        title = stringResource(Res.string.about_title),
        navigationIcon = TakaTopBarNavigationIcon.Back,
        onNavigationClick = onBackClick,
      )
    },
  ) { innerPadding ->
    Column(
      modifier = modifier
        .padding(innerPadding)
        .padding(24.dp),
      verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
      Text(
        text = stringResource(Res.string.about_description),
        style =  MaterialTheme.typography.bodyLarge,
      )
    }
  }
}