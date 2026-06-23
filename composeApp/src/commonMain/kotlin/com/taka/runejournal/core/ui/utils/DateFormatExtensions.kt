package com.taka.runejournal.core.ui.utils

import nl.jacobras.humanreadable.HumanReadable
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

fun Instant.formatRelative(now: Instant = Clock.System.now()): String {
  return HumanReadable.timeAgo(this, now)
}

expect fun Instant.formatAbsolute(): String