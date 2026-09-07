package com.taka.runejournal.feature.timeline.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaCard
import com.taka.runejournal.core.ui.components.TakaPagerIndicator
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaIconButtonSize
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import com.taka.runejournal.core.ui.theme.TakaSpaceSm
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.ic_new_reading_icon
import taka_rune_journal.composeapp.generated.resources.book_with_taka_symbol
import taka_rune_journal.composeapp.generated.resources.cloth_bag_with_runes
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_first_reading
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_intro
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_intro_title
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_readings
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_readings_title
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_runes
import taka_rune_journal.composeapp.generated.resources.timeline_welcome_slide_runes_title

@Preview
@Composable
fun WelcomeSection(
  onNewReadingClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val pagerState = rememberPagerState (
    pageCount = { 3 },
  )
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top,
  ) {
    TakaCard(
      modifier = modifier.weight(1f)
    ) {
      HorizontalPager(
        state = pagerState,
      ) { page ->
        when (page) {
          0 -> IntroSlide()
          1 -> RunesSlide()
          2 -> ReadingsSlide(onNewReadingClick)
        }
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
fun IntroSlide() {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top,
  ) {
    Text(
      modifier = Modifier
        .align(Alignment.CenterHorizontally),
      text = stringResource(Res.string.timeline_welcome_slide_intro_title),
      style = MaterialTheme.typography.headlineMedium
    )
    Image(
      painter = painterResource(Res.drawable.book_with_taka_symbol),
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
      text = stringResource(Res.string.timeline_welcome_slide_intro),
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}

@Composable
fun RunesSlide() {
  Column(
    modifier = Modifier.fillMaxSize(),
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
  }
}
@Composable
fun ReadingsSlide(
  onNewReadingClick: () -> Unit = {}
) {
  Column(
    modifier = Modifier.fillMaxSize(),
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

//@Composable
//fun DisplayNameTextField(
//    onSaveName: (String) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//  val focusManager = LocalFocusManager.current
//  var nameInput by rememberSaveable { mutableStateOf("") }
//
//  TakaTextField(
//      value = nameInput,
//      onValueChange = { nameInput = it },
//      label = stringResource(Res.string.timeline_textfield_label_your_name),
//      singleLine = true,
//      keyboardOptions = KeyboardOptions(
//          imeAction = ImeAction.Done,
//      ),
//      keyboardActions = KeyboardActions(
//          onDone = {
//            focusManager.clearFocus() // trigger the onFocusChanged lambda
//          },
//      ),
//      modifier = modifier
//        .onFocusChanged() { focusState ->
//          if (!focusState.isFocused) {
//            onSaveName(nameInput)
//          }
//      }
//  )
//}