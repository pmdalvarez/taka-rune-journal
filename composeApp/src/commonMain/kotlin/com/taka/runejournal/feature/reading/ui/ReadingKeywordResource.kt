package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingCategory
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.core.domain.model.RuneOrientation
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.allStringResources
import taka_rune_journal.composeapp.generated.resources.keywords_algiz_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_algiz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_ansuz_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_ansuz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_berkano_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_berkano_upright
import taka_rune_journal.composeapp.generated.resources.keywords_dagaz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_ehwaz_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_ehwaz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_eihwaz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_fehu_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_fehu_upright
import taka_rune_journal.composeapp.generated.resources.keywords_gebo_upright
import taka_rune_journal.composeapp.generated.resources.keywords_hagalaz_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_hagalaz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_ingwaz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_isa_upright
import taka_rune_journal.composeapp.generated.resources.keywords_jera_upright
import taka_rune_journal.composeapp.generated.resources.keywords_kenaz_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_kenaz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_laguz_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_laguz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_mannaz_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_mannaz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_nauthiz_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_nauthiz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_othala_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_othala_upright
import taka_rune_journal.composeapp.generated.resources.keywords_perthro_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_perthro_upright
import taka_rune_journal.composeapp.generated.resources.keywords_raidho_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_raidho_upright
import taka_rune_journal.composeapp.generated.resources.keywords_sowilo_upright
import taka_rune_journal.composeapp.generated.resources.keywords_thurisaz_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_thurisaz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_tiwaz_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_tiwaz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_uruz_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_uruz_upright
import taka_rune_journal.composeapp.generated.resources.keywords_wunjo_reversed
import taka_rune_journal.composeapp.generated.resources.keywords_wunjo_upright

// General keywords MUST exist and hence hard coded to ensure they exist
fun DrawnRune.generalKeywords(): StringResource =
  if (orientation == RuneOrientation.REVERSED && rune.isReversible) {
    reversedInterpretation(rune)
  } else {
    uprightInterpretation(rune)
  }

private fun uprightInterpretation(rune: Rune): StringResource = when (rune) {
  Rune.FEHU -> Res.string.keywords_fehu_upright
  Rune.URUZ -> Res.string.keywords_uruz_upright
  Rune.THURISAZ -> Res.string.keywords_thurisaz_upright
  Rune.ANSUZ -> Res.string.keywords_ansuz_upright
  Rune.RAIDHO -> Res.string.keywords_raidho_upright
  Rune.KENAZ -> Res.string.keywords_kenaz_upright
  Rune.GEBO -> Res.string.keywords_gebo_upright
  Rune.WUNJO -> Res.string.keywords_wunjo_upright
  Rune.HAGALAZ -> Res.string.keywords_hagalaz_upright
  Rune.NAUTHIZ -> Res.string.keywords_nauthiz_upright
  Rune.ISA -> Res.string.keywords_isa_upright
  Rune.JERA -> Res.string.keywords_jera_upright
  Rune.EIHWAZ -> Res.string.keywords_eihwaz_upright
  Rune.PERTHRO -> Res.string.keywords_perthro_upright
  Rune.ALGIZ -> Res.string.keywords_algiz_upright
  Rune.SOWILO -> Res.string.keywords_sowilo_upright
  Rune.TIWAZ -> Res.string.keywords_tiwaz_upright
  Rune.BERKANO -> Res.string.keywords_berkano_upright
  Rune.EHWAZ -> Res.string.keywords_ehwaz_upright
  Rune.MANNAZ -> Res.string.keywords_mannaz_upright
  Rune.LAGUZ -> Res.string.keywords_laguz_upright
  Rune.INGWAZ -> Res.string.keywords_ingwaz_upright
  Rune.DAGAZ -> Res.string.keywords_dagaz_upright
  Rune.OTHALA -> Res.string.keywords_othala_upright
}

private fun reversedInterpretation(rune: Rune): StringResource = when (rune) {
  Rune.FEHU -> Res.string.keywords_fehu_reversed
  Rune.URUZ -> Res.string.keywords_uruz_reversed
  Rune.THURISAZ -> Res.string.keywords_thurisaz_reversed
  Rune.ANSUZ -> Res.string.keywords_ansuz_reversed
  Rune.RAIDHO -> Res.string.keywords_raidho_reversed
  Rune.KENAZ -> Res.string.keywords_kenaz_reversed
  //  Rune.GEBO not reversible
  Rune.WUNJO -> Res.string.keywords_wunjo_reversed
  Rune.HAGALAZ -> Res.string.keywords_hagalaz_reversed
  Rune.NAUTHIZ -> Res.string.keywords_nauthiz_reversed
  //  Rune.ISA not reversible
  //  Rune.JERA not reversible
  //  Rune.EIHWAZ not reversible
  Rune.PERTHRO -> Res.string.keywords_perthro_reversed
  Rune.ALGIZ -> Res.string.keywords_algiz_reversed
  //  Rune.SOWILO not reversible
  Rune.TIWAZ -> Res.string.keywords_tiwaz_reversed
  Rune.BERKANO -> Res.string.keywords_berkano_reversed
  Rune.EHWAZ -> Res.string.keywords_ehwaz_reversed
  Rune.MANNAZ -> Res.string.keywords_mannaz_reversed
  Rune.LAGUZ -> Res.string.keywords_laguz_reversed
  //  Rune.INGWAZ not reversible
  //  Rune.DAGAZ not reversible
  Rune.OTHALA -> Res.string.keywords_othala_reversed
  else -> error("Unknown reversed rune: $rune")
}

// fetches supplemental keywords to the general interpretation (for certain rune/orientation/category combinations) if found
fun DrawnRune.supplementalKeywords(readingCategory: ReadingCategory): StringResource? {
  if (readingCategory == ReadingCategory.GENERAL) return null
  val key = "keywords_${rune.key}_${orientation.key}_${readingCategory.key}"
  return Res.allStringResources[key]
}

fun String.toDotSeparatedKeywords(): String {
  return this.split(",")
    .map { it.trim() }
    .joinToString(" · ")
}
