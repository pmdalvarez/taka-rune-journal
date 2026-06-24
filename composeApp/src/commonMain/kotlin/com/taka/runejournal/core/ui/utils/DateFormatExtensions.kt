package com.taka.runejournal.core.ui.utils

import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

fun Long.toDateString(): String = Instant.fromEpochMilliseconds(this).format()

fun Instant.format(): String {
  val now = Clock.System.now()
  val diff = now - this
  return when {
    diff < 7.days -> this.formatRelative(now)
    else -> this.formatAbsolute()
  }
}

expect fun Instant.formatRelative(now: Instant): String

expect fun Instant.formatAbsolute(): String