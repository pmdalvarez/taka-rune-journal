package com.taka.runejournal.core.domain.model

enum class RuneSpread(
  val key: String, // Changing key values can break existing references in db or string files
) {
  SINGLE_RUNE("single") ,
  PAST_PRESENT_FUTURE("ppf");

  companion object {
    fun fromKey(key: String): RuneOrientation? =
      RuneOrientation.entries.firstOrNull { it.key == key }
  }
}