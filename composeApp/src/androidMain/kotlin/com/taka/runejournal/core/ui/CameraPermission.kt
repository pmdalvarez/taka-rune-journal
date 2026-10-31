package com.taka.runejournal.core.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberCameraPermission(): Boolean {
  val context = LocalContext.current
  var isGranted by remember {
    mutableStateOf(
      context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    )
  }
  val permissionLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
  ) { granted ->
    isGranted = granted
  }

  LaunchedEffect(Unit) {
    if (!isGranted) {
      permissionLauncher.launch(Manifest.permission.CAMERA)
    }
  }

  return isGranted
}
