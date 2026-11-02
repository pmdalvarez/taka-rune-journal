package com.taka.runejournal.feature.reading.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.conamobile.pdfkmp.viewer.PdfViewer
import com.conamobile.pdfkmp.viewer.rememberPdfSaveAction
import com.conamobile.pdfkmp.viewer.rememberPdfShareAction
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarAction
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_share_preview_topbar_title

@Composable
fun ReadingPdfPreviewScreen(
  viewModel: ReadingPdfPreviewViewModel,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val document = uiState.document
  val shareAction = rememberPdfShareAction()
  val saveAction = rememberPdfSaveAction()

  TakaScaffold(
    modifier = modifier,
    // The PDF fills the whole area below the top bar
    contentPadding = PaddingValues(0.dp),
    topBar = {
      TakaTopBar(
        title = stringResource(Res.string.reading_share_preview_topbar_title),
        navigationIcon = TakaTopBarNavigationIcon.Back,
        onNavigationClick = onBackClick,
        action = TakaTopBarAction.PdfPreviewActions(
          onShareClick = { document?.let { shareAction(it.toByteArray(), uiState.fileName) } },
          onDownloadClick = { document?.let { saveAction(it.toByteArray(), uiState.fileName) } },
        ),
      )
    },
  ) { contentModifier ->
    if (document == null) {
      Box(
        modifier = contentModifier,
        contentAlignment = Alignment.Center,
      ) {
        CircularProgressIndicator()
      }
    } else {
      PdfViewer(
        document = document,
        modifier = contentModifier,
        showShareButton = false,
        showZoomControls = false,
        showPageIndicator = false,
        backgroundColor = MaterialTheme.colorScheme.background,
      )
    }
  }
}
