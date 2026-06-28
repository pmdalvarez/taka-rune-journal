package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.ReadingCategory
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_category_general_topbar_title
import taka_rune_journal.composeapp.generated.resources.reading_category_purpose_topbar_title
import taka_rune_journal.composeapp.generated.resources.reading_category_relationships_topbar_title
import taka_rune_journal.composeapp.generated.resources.reading_category_security_topbar_title
import taka_rune_journal.composeapp.generated.resources.reading_category_self_topbar_title

fun ReadingCategory.readingType(): StringResource = when (this) {
  ReadingCategory.GENERAL -> Res.string.reading_category_general_topbar_title
  ReadingCategory.RELATIONSHIPS -> Res.string.reading_category_relationships_topbar_title
  ReadingCategory.PURPOSE -> Res.string.reading_category_purpose_topbar_title
  ReadingCategory.SECURITY -> Res.string.reading_category_security_topbar_title
  ReadingCategory.SELF -> Res.string.reading_category_self_topbar_title
}
