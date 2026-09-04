package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaIconButtonSize
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceSm
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.ic_new_reading_icon
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_first_reading
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_greeting
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_prompt

@Composable
fun WelcomeSection(
  onNewReadingClick: () -> Unit = {},
  modifier: Modifier
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(TakaSectionSpacing),
  ) {
    Text(
      text = stringResource(Res.string.timeline_welcome_greeting),
      style = MaterialTheme.typography.headlineMedium
    )
    Text(
      text = stringResource(Res.string.timeline_welcome_prompt),
      modifier = Modifier.padding(top = TakaContentSpacing).fillMaxWidth(),
      style = MaterialTheme.typography.bodyLarge
    )
    TakaButton(
      onClick = onNewReadingClick
    ) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(TakaSpaceSm),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Icon(
          painter = painterResource(Res.drawable.ic_new_reading_icon),
          contentDescription = null,
          modifier = Modifier.size(TakaIconButtonSize),
          tint = MaterialTheme.colorScheme.onPrimary
        )
        Text(stringResource(Res.string.timeline_button_new_first_reading))
      }
    }
  }
}