import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.theme.TakaCardSpacing
import com.taka.runejournal.feature.more.ui.components.GlossaryRow
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.glossary_title

@Preview
@Composable
fun GlossaryScreen(
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
      verticalArrangement = Arrangement.spacedBy(TakaCardSpacing),
    ) {
      for (rune in Rune.entries) {
        GlossaryRow(
          rune = rune,
          onRuneClick = {}
        )
      }
    }
  }
}