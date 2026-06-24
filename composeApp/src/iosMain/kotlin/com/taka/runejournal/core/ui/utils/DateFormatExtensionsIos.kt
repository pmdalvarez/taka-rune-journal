package com.taka.runejournal.core.ui.utils

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSDateFormatterMediumStyle
import platform.Foundation.NSDateFormatterNoStyle
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970
import kotlin.time.Instant

actual fun Instant.formatRelative(now: Instant) = this.formatAbsolute() // TODO this needs to be implemented properly

actual fun Instant.formatAbsolute(): String {
  val date = NSDate.dateWithTimeIntervalSince1970(this.toEpochMilliseconds().toDouble() / 1000.0)
  val formatter = NSDateFormatter().apply {
    locale = NSLocale.currentLocale
    dateStyle = NSDateFormatterMediumStyle  // "Jan 15, 2025" or "15 Jan 2025" depending on locale
    timeStyle = NSDateFormatterNoStyle
  }
  return formatter.stringFromDate(date)
}