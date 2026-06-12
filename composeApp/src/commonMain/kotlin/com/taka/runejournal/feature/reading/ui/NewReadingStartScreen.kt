package com.taka.runejournal.feature.reading.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taka.runejournal.core.domain.model.RuneId
import com.taka.runejournal.core.ui.drawable
import org.jetbrains.compose.resources.painterResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.ic_app_icon
import taka_rune_journal.composeapp.generated.resources.rune_empty
import kotlin.collections.chunked
import kotlin.collections.forEach

@Composable
fun NewReadingStartScreen(
  viewModel: ReadingViewModel,
  onBackClick: () -> Unit,
  onContinueClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(24.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Reading start screen",
      style = MaterialTheme.typography.headlineMedium
    )
  }
}

@Preview
@Composable
private fun RunePreview() {
  Column(
    modifier = Modifier
      .background(Color.White)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Image(
          painter = painterResource(Res.drawable.rune_empty),
          contentDescription = "empty",
          modifier = Modifier.size(width = 48.dp, height = 72.dp)
        )

        Text(
          text = "empty",
          modifier = Modifier.padding(top = 4.dp),
          color = Color.Black,
          fontSize = 10.sp
        )
      }
      Column(
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Image(
          painter = painterResource(Res.drawable.ic_app_icon),
          contentDescription = "empty",
          modifier = Modifier.size(width = 48.dp, height = 72.dp)
        )

        Text(
          text = "app icon",
          modifier = Modifier.padding(top = 4.dp),
          color = Color.Black,
          fontSize = 10.sp
        )
      }
    }
    RuneId.entries
      .chunked(4)
      .forEach { rowRunes ->
        Row(
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          rowRunes.forEach { runeId ->
            Column(
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Image(
                painter = painterResource(runeId.drawable()),
                contentDescription = runeId.key,
                modifier = Modifier.size(width = 48.dp, height = 72.dp)
              )

              Text(
                text = runeId.key,
                modifier = Modifier.padding(top = 4.dp),
                color = Color.Black,
                fontSize = 10.sp
              )
            }
          }
        }
      }
  }
}