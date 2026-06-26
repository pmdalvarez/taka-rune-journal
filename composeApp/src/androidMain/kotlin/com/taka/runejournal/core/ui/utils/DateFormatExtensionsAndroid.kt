package com.taka.runejournal.core.ui.utils

import android.icu.text.DisplayContext
import android.icu.text.RelativeDateTimeFormatter
import android.icu.util.ULocale
import android.util.Log
import androidx.core.os.LocaleListCompat
import java.time.Duration
import kotlin.time.Instant
import kotlin.time.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

object LocaleProvider {
  // Unfortunately we need to maintain a hard-coded list of supported languages here, currently no code that can fetch this info
  private val supportedLocales = listOf(
    Locale.ENGLISH
  )
  private val defaultLocale = Locale.ENGLISH

  val currentLocale: Locale
    get() {
      val deviceLocale = LocaleListCompat.getDefault()[0] ?: Locale.getDefault()
      return supportedLocales.firstOrNull { it.language == deviceLocale.language }
        ?: defaultLocale
    }
}
actual fun Instant.formatRelative(now: Instant): String {
  val duration = Duration.between(
    this.toJavaInstant(),
    now.toJavaInstant(),
  )

  val formatter = RelativeDateTimeFormatter.getInstance(
    ULocale.forLocale(LocaleProvider.currentLocale),
    null,
    RelativeDateTimeFormatter.Style.NARROW,
    DisplayContext.CAPITALIZATION_NONE
  )

  val isFuture = duration.isNegative
  val absoluteDuration = duration.abs()

  val direction = if (isFuture) {
    RelativeDateTimeFormatter.Direction.NEXT
  } else {
    RelativeDateTimeFormatter.Direction.LAST
  }

  return when {
    absoluteDuration.toMinutes() < 1 -> {
      formatter.format(
        RelativeDateTimeFormatter.Direction.PLAIN,
        RelativeDateTimeFormatter.AbsoluteUnit.NOW,
      )
    }

    absoluteDuration.toHours() < 1 -> {
      formatter.format(
        absoluteDuration.toMinutes().toDouble(),
        direction,
        RelativeDateTimeFormatter.RelativeUnit.MINUTES,
      )
    }

    absoluteDuration.toDays() < 1 -> {
      formatter.format(
        absoluteDuration.toHours().toDouble(),
        direction,
        RelativeDateTimeFormatter.RelativeUnit.HOURS,
      )
    }

    else -> {
      formatter.format(
        absoluteDuration.toDays().toDouble(),
        direction,
        RelativeDateTimeFormatter.RelativeUnit.DAYS,
      )
    }
  }
}
actual fun Instant.formatAbsolute(): String {
  return toJavaInstant()
    .atZone(ZoneId.systemDefault())
    .format(
      DateTimeFormatter
        .ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(LocaleProvider.currentLocale)
    )
}