package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingCategory
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.allStringResources

fun getReadingInterpretationRes(
  drawnRune: DrawnRune,
  readingCategory: ReadingCategory,
): StringResource? {
  val specificKey = getReadingInterpretationKey(
    drawnRune = drawnRune,
    readingCategory = readingCategory,
  )

  val generalKey = getReadingInterpretationKey(
    drawnRune = drawnRune,
    readingCategory = ReadingCategory.GENERAL,
  )

  return Res.allStringResources[specificKey]
    ?: Res.allStringResources[generalKey]
}

private fun getReadingInterpretationKey(drawnRune: DrawnRune, readingCategory: ReadingCategory): String {
  return "interpretation_${drawnRune.rune.key}_${drawnRune.orientation.key}_${readingCategory.key}"
}