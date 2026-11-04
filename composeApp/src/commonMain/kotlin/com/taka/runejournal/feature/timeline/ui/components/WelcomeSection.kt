package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taka.runejournal.core.ui.components.FadingScrollColumn
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaCard
import com.taka.runejournal.core.ui.components.TakaPagerIndicator
import com.taka.runejournal.core.ui.components.TakaTextAction
import com.taka.runejournal.core.ui.components.TakaTextField
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaIconButtonSize
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceSm
import com.taka.runejournal.core.ui.theme.TakaWelcomeBrandGap
import com.taka.runejournal.core.ui.theme.TakaWelcomeBrandIconHeight
import com.taka.runejournal.core.ui.theme.TakaWelcomeBrandIconWidth
import com.taka.runejournal.core.ui.theme.TakaWelcomeWordmarkVerticalOffset
import com.taka.runejournal.core.ui.theme.wordmarkFontFamily
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.app_name
import taka_rune_journal.composeapp.generated.resources.button_continue
import taka_rune_journal.composeapp.generated.resources.cloth_bag_with_runes
import taka_rune_journal.composeapp.generated.resources.ic_new_reading_icon
import taka_rune_journal.composeapp.generated.resources.ic_topbar_icon
import taka_rune_journal.composeapp.generated.resources.journal_with_tea_and_runes
import taka_rune_journal.composeapp.generated.resources.open_book
import taka_rune_journal.composeapp.generated.resources.settings_your_name
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_first_reading
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_import_reading
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_intro
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_intro_title
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_readings
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_readings_title
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_runes
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_runes_title
import taka_rune_journal.composeapp.generated.resources.timeline_your_name_prompt

@Preview
@Composable
fun WelcomeSection(
  onDisplayNameEntered: (String) -> Unit = {},
  onNewReadingClick: () -> Unit = {},
  onImportReadingClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val pagerState = rememberPagerState (
    pageCount = { 3 },
  )
  val coroutineScope = rememberCoroutineScope()
  val onContinueClick: () -> Unit = {
    coroutineScope.launch {
      pagerState.animateScrollToPage(pagerState.currentPage + 1)
    }
  }
    Column (
      modifier = Modifier
        .fillMaxSize()
        .imePadding(), // ensures keyboard doesn't hide enter name field
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Top,
    ) {
    HorizontalPager(
      modifier = modifier.weight(1f),
      state = pagerState
    ) { page ->
      when (page) {
        0 -> IntroSlide(onDisplayNameEntered, onContinueClick)
        1 -> RunesSlide(onContinueClick)
        2 -> ReadingsSlide(onNewReadingClick, onImportReadingClick)
      }
    }
    // TODO - figure out where the spacing between card and indicator is coming from, because it isn't visible in preview
    TakaPagerIndicator(
      pageCount = pagerState.pageCount,
      currentPage = pagerState.currentPage,
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom=TakaContentSpacing),
    )
  }
}

@Composable
fun IntroSlide(
  onDisplayNameEntered: (String) -> Unit = {},
  onContinueClick: () -> Unit = {}
) {
  FadingScrollColumn(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top,
  ) {
    WelcomeBrandTitle(
      modifier = Modifier.align(Alignment.CenterHorizontally),
    )
    Image(
      painter = painterResource(Res.drawable.journal_with_tea_and_runes),
      contentDescription = "test",
      contentScale = ContentScale.Fit, // the scene is wider than it is tall, so it fits the width
      colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
      modifier = Modifier
        .height(288.dp)
        .fillMaxWidth()
        .align(Alignment.CenterHorizontally)
        .padding(top = TakaContentSpacing)
        .padding(TakaSectionSpacing),
    )
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = TakaContentSpacing),
      text = stringResource(Res.string.timeline_welcome_slide_intro) + "\n\n" + stringResource(Res.string.timeline_your_name_prompt),
      style = MaterialTheme.typography.bodyMedium,
    )
    DisplayNameTextField(
      onSaveName = onDisplayNameEntered,
      modifier = Modifier
      .padding(top = TakaContentSpacing))
    TakaButton(
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(top = TakaContentSpacing),
      onClick = onContinueClick
    ) {
      Text(stringResource(Res.string.button_continue))
    }
  }
}

/**
 * "Welcome to", the brand symbol and TAKA in one row. Both sides take the same width,
 * so the symbol sits exactly in the centre.
 */
@Composable
private fun WelcomeBrandTitle(
  modifier: Modifier = Modifier,
) {
  val brandColor = MaterialTheme.colorScheme.primary
  Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = stringResource(Res.string.timeline_welcome_slide_intro_title),
      modifier = Modifier
        .weight(1f)
        .padding(end = TakaWelcomeBrandGap),
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      textAlign = TextAlign.End,
      maxLines = 1,
    )
    Image(
      painter = painterResource(Res.drawable.ic_topbar_icon),
      contentDescription = null,
      modifier = Modifier
        .width(TakaWelcomeBrandIconWidth)
        .height(TakaWelcomeBrandIconHeight),
      contentScale = ContentScale.Crop,
      colorFilter = ColorFilter.tint(brandColor),
    )
    Text(
      text = stringResource(Res.string.app_name).uppercase(),
      modifier = Modifier
        .weight(1f)
        .padding(start = TakaWelcomeBrandGap)
        .offset(y = TakaWelcomeWordmarkVerticalOffset),
      style = MaterialTheme.typography.headlineMedium.copy(
        fontFamily = wordmarkFontFamily(),
        fontWeight = FontWeight.Bold,
        letterSpacing = 4.4.sp,
      ),
      color = brandColor,
      maxLines = 1,
    )
  }
}

@Composable
fun RunesSlide(
  onContinueClick: () -> Unit = {}
) {
  FadingScrollColumn(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top,
  ) {
    Text(
      modifier = Modifier
        .align(Alignment.CenterHorizontally),
      text = stringResource(Res.string.timeline_welcome_slide_runes_title),
      style = MaterialTheme.typography.headlineMedium
    )
    Image(
      painter = painterResource(Res.drawable.cloth_bag_with_runes),
      contentDescription = "test",
      contentScale = ContentScale.FillHeight,
      modifier = Modifier
        .height(288.dp)
        .fillMaxWidth()
        .align(Alignment.CenterHorizontally)
        .padding(top = TakaContentSpacing)
        .padding(TakaSectionSpacing),
    )
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = TakaContentSpacing),
      text = stringResource(Res.string.timeline_welcome_slide_runes),
      style = MaterialTheme.typography.bodyMedium,
    )
    TakaButton(
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(top = TakaContentSpacing),
      onClick = onContinueClick
    ) {
      Text(stringResource(Res.string.button_continue))
    }
  }
}
@Composable
fun ReadingsSlide(
  onNewReadingClick: () -> Unit = {},
  onImportReadingClick: () -> Unit = {}
) {
  FadingScrollColumn(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top,
  ) {
    Text(
      modifier = Modifier
        .align(Alignment.CenterHorizontally),
      text = stringResource(Res.string.timeline_welcome_slide_readings_title),
      style = MaterialTheme.typography.headlineMedium
    )
    Image(
      painter = painterResource(Res.drawable.open_book),
      contentDescription = "test",
      contentScale = ContentScale.FillHeight,
      modifier = Modifier
        .height(260.dp)
        .fillMaxWidth()
        .align(Alignment.CenterHorizontally)
        .padding(top = TakaContentSpacing)
        .padding(TakaSectionSpacing),
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
    TakaTextAction(
      text = stringResource(Res.string.timeline_welcome_import_reading),
      onClick = onImportReadingClick,
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(top = TakaContentSpacing)
    )
  }
}

@Composable
fun DisplayNameTextField(
    onSaveName: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
  val focusManager = LocalFocusManager.current
  var nameInput by rememberSaveable { mutableStateOf("") }

  TakaTextField(
      value = nameInput,
      onValueChange = { nameInput = it },
      label = stringResource(Res.string.settings_your_name),
      singleLine = true,
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
      ),
      keyboardActions = KeyboardActions(
          onDone = {
            focusManager.clearFocus() // trigger the onFocusChanged lambda
          },
      ),
      modifier = modifier
        .onFocusChanged() { focusState ->
          if (!focusState.isFocused) {
            onSaveName(nameInput)
          }
      }
  )
}