package com.taka.runejournal.core.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
actual fun SystemBarIconTheme(darkTheme: Boolean) {
  val view = LocalView.current

  SideEffect {
    val activity = view.context as? Activity ?: return@SideEffect
    val controller = WindowCompat.getInsetsController(activity.window, view)
    controller.isAppearanceLightStatusBars = !darkTheme
    controller.isAppearanceLightNavigationBars = !darkTheme
  }
}