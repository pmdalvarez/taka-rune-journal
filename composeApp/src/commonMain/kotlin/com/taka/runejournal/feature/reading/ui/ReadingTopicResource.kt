package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.ReadingTopic
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_type_general
import taka_rune_journal.composeapp.generated.resources.reading_type_purpose
import taka_rune_journal.composeapp.generated.resources.reading_type_relationships
import taka_rune_journal.composeapp.generated.resources.reading_type_security
import taka_rune_journal.composeapp.generated.resources.reading_type_self

fun ReadingTopic.readingType(): StringResource = when (this) {
  ReadingTopic.GENERAL -> Res.string.reading_type_general
  ReadingTopic.RELATIONSHIPS -> Res.string.reading_type_relationships
  ReadingTopic.PURPOSE -> Res.string.reading_type_purpose
  ReadingTopic.SECURITY -> Res.string.reading_type_security
  ReadingTopic.SELF -> Res.string.reading_type_self
}