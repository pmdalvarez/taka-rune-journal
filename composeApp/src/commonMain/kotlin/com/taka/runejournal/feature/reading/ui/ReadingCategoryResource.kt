package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.ReadingCategory
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_type_general
import taka_rune_journal.composeapp.generated.resources.reading_type_purpose
import taka_rune_journal.composeapp.generated.resources.reading_type_relationships
import taka_rune_journal.composeapp.generated.resources.reading_type_security
import taka_rune_journal.composeapp.generated.resources.reading_type_self

fun ReadingCategory.readingType(): StringResource = when (this) {
  ReadingCategory.GENERAL -> Res.string.reading_type_general
  ReadingCategory.RELATIONSHIPS -> Res.string.reading_type_relationships
  ReadingCategory.PURPOSE -> Res.string.reading_type_purpose
  ReadingCategory.SECURITY -> Res.string.reading_type_security
  ReadingCategory.SELF -> Res.string.reading_type_self
}