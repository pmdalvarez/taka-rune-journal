package com.taka.runejournal.core.domain.model

enum class RuneOrientation(
  val key: String, // Changing key values can break existing references in db or string files
) {
  UPRIGHT("upright") ,
  REVERSED("reversed");

  companion object {
    fun fromKey(key: String): RuneOrientation? =
      RuneOrientation.entries.firstOrNull { it.key == key }
  }
}