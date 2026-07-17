package com.taka.runejournal.core.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
actual fun ImmersiveModeEffect(
  enabled: Boolean,
) {
  val view = LocalView.current
  (view.context as? Activity)?.window?.let {
    val controller = WindowCompat.getInsetsController(it, view)
    DisposableEffect(enabled) {
      if (enabled) {
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
          WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
      } else {
        controller.show(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
          WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
      }

      onDispose {
        controller.show(WindowInsetsCompat.Type.systemBars())
      }
    }
  }
}