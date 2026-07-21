package com.taka.runejournal.core.domain.model

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.ic_ppf_rune_spread_icon
import taka_rune_journal.composeapp.generated.resources.ic_single_rune_spread_icon
import taka_rune_journal.composeapp.generated.resources.reading_ppf_spread_description
import taka_rune_journal.composeapp.generated.resources.reading_ppf_spread_name
import taka_rune_journal.composeapp.generated.resources.reading_single_rune_spread_description
import taka_rune_journal.composeapp.generated.resources.reading_single_rune_spread_name

enum class RuneSpread(
  val icon: DrawableResource,
  val title: StringResource,
  val description: StringResource,
  val runeCount: Int
  ) {
  SINGLE_RUNE(
    Res.drawable.ic_single_rune_spread_icon,
    Res.string.reading_single_rune_spread_name,
    Res.string.reading_single_rune_spread_description,
    1
  ),
  PAST_PRESENT_FUTURE(
    Res.drawable.ic_ppf_rune_spread_icon,
    Res.string.reading_ppf_spread_name,
    Res.string.reading_ppf_spread_description,
    3
  );
}
