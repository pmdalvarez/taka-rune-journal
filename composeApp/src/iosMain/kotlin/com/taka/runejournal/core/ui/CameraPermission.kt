package com.taka.runejournal.core.ui

import androidx.compose.runtime.Composable

@Composable
actual fun rememberCameraPermission(): Boolean {
  // TODO: Implement for iOS (AVCaptureDevice authorization + NSCameraUsageDescription in Info.plist)
  return false
}
