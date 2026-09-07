package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaCard
import com.taka.runejournal.core.ui.components.TakaPagerIndicator
import com.taka.runejournal.core.ui.theme.TakaCardSpacing
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaIconButtonSize
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceSm
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.ic_new_reading_icon
import taka_rune_journal.composeapp.generated.resources.rune_empty
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_first_reading
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_intro
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_intro_title
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_readings
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_readings_title
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_runes
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_runes_title

@Composable
fun WelcomeSection(
  onNewReadingClick: () -> Unit = {},
  modifier: Modifier
) {
  val pagerState = rememberPagerState (
    pageCount = { 3 },
  )
  TakaCard(
    modifier = modifier
  ) {
    HorizontalPager(
      state = pagerState,
      modifier = Modifier.weight(1f),
    ) { page ->
      when (page) {
        0 -> {
          WelcomeSlide(pageCount = pagerState.pageCount, curentPage = pagerState.currentPage) {
            Text(
              modifier = Modifier
                .align(Alignment.CenterHorizontally),
              text = stringResource(Res.string.timeline_welcome_slide_intro_title),
              style = MaterialTheme.typography.headlineMedium
            )
            Image(
              painter = painterResource(Res.drawable.rune_empty),
              contentDescription = "test",
              contentScale = ContentScale.Fit,
              modifier = Modifier
                .size(width = 192.dp, height = 288.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = TakaContentSpacing),
            )
            Text(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = TakaContentSpacing),
              text = stringResource(Res.string.timeline_welcome_slide_intro),
              style = MaterialTheme.typography.bodyMedium,
            )
          }
        }
        1 -> {
          WelcomeSlide(pageCount = pagerState.pageCount, curentPage = pagerState.currentPage) {
            Text(
              modifier = Modifier
                .align(Alignment.CenterHorizontally),
              text = stringResource(Res.string.timeline_welcome_slide_runes_title),
              style = MaterialTheme.typography.headlineMedium
            )
            Image(
              painter = painterResource(Res.drawable.rune_empty),
              contentDescription = "test",
              contentScale = ContentScale.Fit,
              modifier = Modifier
                .size(width = 192.dp, height = 288.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = TakaContentSpacing),
            )
            Text(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = TakaContentSpacing),
              text = stringResource(Res.string.timeline_welcome_slide_runes),
              style = MaterialTheme.typography.bodyMedium,
            )
          }
        }
        2 -> {
          WelcomeSlide(pageCount = pagerState.pageCount, curentPage = pagerState.currentPage) {
            Text(
              modifier = Modifier
                .align(Alignment.CenterHorizontally),
              text = stringResource(Res.string.timeline_welcome_slide_readings_title),
              style = MaterialTheme.typography.headlineMedium
            )
            Image(
              painter = painterResource(Res.drawable.rune_empty),
              contentDescription = "test",
              contentScale = ContentScale.Fit,
              modifier = Modifier
                .size(width = 192.dp, height = 288.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = TakaContentSpacing),
            )
            Text(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = TakaContentSpacing),
              text = stringResource(Res.string.timeline_welcome_slide_readings),
              style = MaterialTheme.typography.bodyMedium,
            )
            TakaButton(
              modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = TakaContentSpacing),
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
      }
    }
  }
}

@Composable
fun WelcomeSlide(
  pageCount: Int,
  curentPage: Int,
  content: @Composable (Modifier) -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top,
  ) {
    content(Modifier)
    Spacer(modifier = Modifier.weight(1f))
    TakaPagerIndicator(
      pageCount = pageCount,
      currentPage = curentPage,
      modifier = Modifier
        .fillMaxWidth()
        .padding(top=TakaCardSpacing),
    )
  }
}