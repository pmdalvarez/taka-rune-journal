package com.taka.runejournal.feature.timeline.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon

@Composable
fun TimelineDetailScreen(
  viewModel: TimelineViewModel,
  timelineItemId: Long?,
  onBackClick: () -> Unit,
  onSaved: () -> Unit,
  modifier: Modifier = Modifier
) {
  TakaScaffold(
    modifier = modifier,
    topBar = {
      TakaTopBar(
        title = "Timeline detail",
        navigationIcon = TakaTopBarNavigationIcon.Back,
        onNavigationClick = onBackClick,
      )
    }
  ) { contentModifier ->
    Column(
      modifier = contentModifier,
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "Timeline detail screen clicked id: $timelineItemId",
        style = MaterialTheme.typography.headlineMedium
      )
    }
  }
}