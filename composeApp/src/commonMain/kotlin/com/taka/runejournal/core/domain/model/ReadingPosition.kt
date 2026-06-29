package com.taka.runejournal.core.domain.model

import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.rune_reading_tab_single_rune

enum class ReadingPosition(
  val key: String) {
  SINGLE("single"),
  PAST("past"),
  PRESENT("present"),
  FUTURE("future");

  companion object {
    fun fromKey(key: String): ReadingCategory? =
      ReadingCategory.entries.firstOrNull { it.key == key }
  }
}