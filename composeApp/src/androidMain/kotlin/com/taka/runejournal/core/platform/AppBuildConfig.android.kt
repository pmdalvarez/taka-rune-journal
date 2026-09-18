package com.taka.runejournal.core.platform

actual object AppBuildConfig {

  private var debug: Boolean = false

  fun init(isDebug: Boolean) {
    debug = isDebug
  }

  actual val isDebug: Boolean
    get() = debug
}