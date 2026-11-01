package com.taka.runejournal.feature.reading.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.components.TakaScaffold
import com.taka.runejournal.core.ui.components.TakaSnackbarHost
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.components.showErrorSnackbar
import com.taka.runejournal.core.ui.rememberCameraPermission
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import com.taka.runejournal.core.ui.theme.TakaSectionSpacing
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.ncgroup.kscan.BarcodeFormat
import org.ncgroup.kscan.BarcodeResult
import org.ncgroup.kscan.ScannerView
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_import_camera_permission_denied
import taka_rune_journal.composeapp.generated.resources.reading_import_instruction
import taka_rune_journal.composeapp.generated.resources.reading_import_topbar_title

private const val SCAN_AREA_WIDTH_FRACTION = 0.7f

@Composable
fun ImportReadingScreen(
  viewModel: ImportReadingViewModel,
  onBackClick: () -> Unit,
  onNavigateToReading: (Long) -> Unit,
  modifier: Modifier = Modifier
) {
  val snackbarHostState = remember { SnackbarHostState() }
  val isCameraPermissionGranted = rememberCameraPermission()

  // KScan stops scanning after the first match, so each scanner session calls importReading at most once.
  // After a failed import the scanner restarts with a new session and skips the code that just failed.
  var scannerSession by remember { mutableIntStateOf(0) }
  var lastScannedCode by remember { mutableStateOf<String?>(null) }
  var rejectedCode by remember { mutableStateOf<String?>(null) }

  LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
      when (event) {
        is UiEvent.ShowError -> {
          rejectedCode = lastScannedCode
          scannerSession++
          snackbarHostState.showErrorSnackbar(message = getString(event.messageRes))
        }
        is UiEvent.NavigateToItem -> onNavigateToReading(event.itemId)
        else -> {} // No other events expected
      }
    }
  }

  TakaScaffold(
    modifier = modifier,
    snackbarHost = { TakaSnackbarHost(hostState = snackbarHostState) },
    topBar = {
      TakaTopBar(
        title = stringResource(Res.string.reading_import_topbar_title),
        navigationIcon = TakaTopBarNavigationIcon.Back,
        onNavigationClick = onBackClick,
      )
    },
  ) { contentModifier ->
    Column(
      modifier = contentModifier,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      val scanAreaShape = MaterialTheme.shapes.extraLarge

      Text(
        text = stringResource(Res.string.reading_import_instruction),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
      )

      // Equal flexible space above and below keeps the scan area centred in the space under the heading
      Spacer(
        modifier = Modifier
          .heightIn(min = TakaSectionSpacing)
          .weight(1f)
      )

      Box(
        modifier = Modifier
          .fillMaxWidth(SCAN_AREA_WIDTH_FRACTION)
          .aspectRatio(1f)
          .clip(scanAreaShape)
          .background(MaterialTheme.colorScheme.surfaceContainer),
        contentAlignment = Alignment.Center,
      ) {
        if (isCameraPermissionGranted) {
          key(scannerSession) {
            ScannerView(
              codeTypes = listOf(BarcodeFormat.FORMAT_QR_CODE),
              modifier = Modifier.fillMaxSize(),
              filter = { barcode -> barcode.data != rejectedCode },
            ) { result ->
              when (result) {
                is BarcodeResult.OnSuccess -> {
                  lastScannedCode = result.barcode.data
                  viewModel.importReading(result.barcode.data)
                }
                is BarcodeResult.OnFailed -> {} // Camera errors are not handled yet
              }
            }
          }
          // The Android camera preview is a SurfaceView, which ignores clip(), so the corners are masked instead
          ScanAreaCornerMask(
            shape = scanAreaShape,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.matchParentSize(),
          )
        } else {
          Text(
            text = stringResource(Res.string.reading_import_camera_permission_denied),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(TakaContentSpacing),
          )
        }
      }

      Spacer(modifier = Modifier.weight(1f))
    }
  }
}

/**
 * Paints [color] everywhere in its bounds except inside [shape], hiding the square corners
 * of content underneath that cannot be clipped.
 */
@Composable
private fun ScanAreaCornerMask(
  shape: Shape,
  color: Color,
  modifier: Modifier = Modifier,
) {
  Canvas(
    modifier = modifier.graphicsLayer {
      compositingStrategy = CompositingStrategy.Offscreen
    }
  ) {
    drawRect(color = color)
    drawOutline(
      outline = shape.createOutline(size, layoutDirection, this),
      color = Color.Black,
      blendMode = BlendMode.Clear,
    )
  }
}
