package com.taka.runejournal.core.platform

import com.taka.runejournal.BuildConfig

actual object AppBuildConfig {
  actual val isDebug: Boolean = BuildConfig.DEBUG
}