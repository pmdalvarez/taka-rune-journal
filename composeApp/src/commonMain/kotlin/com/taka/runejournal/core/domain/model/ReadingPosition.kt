package com.taka.runejournal.core.domain.model

enum class ReadingPosition(
  val key: String,
) {
  SINGLE_RUNE("single_rune"),
  PAST("past"),
  PRESENT("present"),
  FUTURE("future");

  companion object {
    fun fromKey(key: String): ReadingCategory? =
      ReadingCategory.entries.firstOrNull { it.key == key }
  }
}