package com.taka.runejournal.core.platform

import android.content.Context
import android.content.pm.ApplicationInfo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual object AppBuildConfig : KoinComponent {

  private val context: Context by inject()

  actual val isDebug: Boolean
    get() = context.applicationInfo.flags and
        ApplicationInfo.FLAG_DEBUGGABLE != 0
}