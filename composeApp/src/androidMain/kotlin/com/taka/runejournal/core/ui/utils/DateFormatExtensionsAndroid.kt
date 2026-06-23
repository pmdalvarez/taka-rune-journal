package com.taka.runejournal.core.ui.utils

import kotlin.time.Instant
import kotlin.time.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

actual fun Instant.formatAbsolute(): String {
  return toJavaInstant()
    .atZone(ZoneId.systemDefault())
    .format(
      DateTimeFormatter
        .ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())
    )
}