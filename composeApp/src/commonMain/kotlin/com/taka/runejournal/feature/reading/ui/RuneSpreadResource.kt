package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.RuneSpread
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_type_general
import taka_rune_journal.composeapp.generated.resources.reading_type_relationships

fun RuneSpread.name(): StringResource = when (this) {
  RuneSpread.SINGLE_RUNE -> Res.string.reading_type_general
  RuneSpread.PAST_PRESENT_FUTURE -> Res.string.reading_type_relationships
}

fun RuneSpread.description(): StringResource = when (this) {
  RuneSpread.SINGLE_RUNE -> Res.string.reading_type_general
  RuneSpread.PAST_PRESENT_FUTURE -> Res.string.reading_type_relationships
}