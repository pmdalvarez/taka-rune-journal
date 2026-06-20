package com.taka.runejournal.core.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable

enum class TakaSnackbarType {
  Info,
  Error,
}

data class TakaSnackbarVisuals(
  override val message: String,
  val type: TakaSnackbarType = TakaSnackbarType.Info,
  override val actionLabel: String? = null,
  override val withDismissAction: Boolean = false,
  override val duration: SnackbarDuration = SnackbarDuration.Short,
) : SnackbarVisuals

@Composable
fun TakaSnackbarHost(hostState: SnackbarHostState) {
  SnackbarHost(hostState = hostState) { snackbarData ->
    val visuals = snackbarData.visuals as? TakaSnackbarVisuals
    val type = visuals?.type ?: TakaSnackbarType.Info

    val containerColor = when (type) {
      TakaSnackbarType.Info -> MaterialTheme.colorScheme.inverseSurface
      TakaSnackbarType.Error -> MaterialTheme.colorScheme.errorContainer
    }

    val contentColor = when (type) {
      TakaSnackbarType.Info -> MaterialTheme.colorScheme.inverseOnSurface
      TakaSnackbarType.Error -> MaterialTheme.colorScheme.onErrorContainer
    }

    Snackbar(
      snackbarData = snackbarData,
      containerColor = containerColor,
      contentColor = contentColor,
      actionColor = contentColor,
      dismissActionContentColor = contentColor,
    )
  }
}

suspend fun SnackbarHostState.showErrorSnackbar(
  message: String,
  actionLabel: String? = null,
  withDismissAction: Boolean = false,
  duration: SnackbarDuration = SnackbarDuration.Short,
) {
  showSnackbar(
    TakaSnackbarVisuals(
      message = message,
      type = TakaSnackbarType.Error,
      actionLabel = actionLabel,
      withDismissAction = withDismissAction,
      duration = duration,
    )
  )
}

suspend fun SnackbarHostState.showInfoSnackbar(
  message: String,
  actionLabel: String? = null,
  withDismissAction: Boolean = false,
  duration: SnackbarDuration = SnackbarDuration.Short,
) {
  showSnackbar(
    TakaSnackbarVisuals(
      message = message,
      type = TakaSnackbarType.Info,
      actionLabel = actionLabel,
      withDismissAction = withDismissAction,
      duration = duration,
    )
  )
}