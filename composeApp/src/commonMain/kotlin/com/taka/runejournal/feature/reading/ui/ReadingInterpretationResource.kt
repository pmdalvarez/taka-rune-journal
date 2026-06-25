package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingCategory
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.core.domain.model.RuneOrientation
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.allStringResources
import taka_rune_journal.composeapp.generated.resources.interpretation_algiz_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_algiz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_ansuz_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_ansuz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_berkano_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_berkano_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_dagaz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_ehwaz_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_ehwaz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_eihwaz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_fehu_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_fehu_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_gebo_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_hagalaz_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_hagalaz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_ingwaz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_isa_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_jera_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_kenaz_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_kenaz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_laguz_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_laguz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_mannaz_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_mannaz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_nauthiz_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_nauthiz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_othala_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_othala_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_perthro_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_perthro_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_raidho_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_raidho_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_sowilo_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_thurisaz_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_thurisaz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_tiwaz_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_tiwaz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_uruz_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_uruz_upright
import taka_rune_journal.composeapp.generated.resources.interpretation_wunjo_reversed
import taka_rune_journal.composeapp.generated.resources.interpretation_wunjo_upright

// General interpretations MUST exist and hence hard coded to ensure they exist
fun DrawnRune.generalInterpretation(): StringResource =
  if (orientation == RuneOrientation.REVERSED && rune.isReversible) {
    reversedInterpretation(rune)
  } else {
    uprightInterpretation(rune)
  }

private fun uprightInterpretation(rune: Rune): StringResource = when (rune) {
  Rune.FEHU -> Res.string.interpretation_fehu_upright
  Rune.URUZ -> Res.string.interpretation_uruz_upright
  Rune.THURISAZ -> Res.string.interpretation_thurisaz_upright
  Rune.ANSUZ -> Res.string.interpretation_ansuz_upright
  Rune.RAIDHO -> Res.string.interpretation_raidho_upright
  Rune.KENAZ -> Res.string.interpretation_kenaz_upright
  Rune.GEBO -> Res.string.interpretation_gebo_upright
  Rune.WUNJO -> Res.string.interpretation_wunjo_upright
  Rune.HAGALAZ -> Res.string.interpretation_hagalaz_upright
  Rune.NAUTHIZ -> Res.string.interpretation_nauthiz_upright
  Rune.ISA -> Res.string.interpretation_isa_upright
  Rune.JERA -> Res.string.interpretation_jera_upright
  Rune.EIHWAZ -> Res.string.interpretation_eihwaz_upright
  Rune.PERTHRO -> Res.string.interpretation_perthro_upright
  Rune.ALGIZ -> Res.string.interpretation_algiz_upright
  Rune.SOWILO -> Res.string.interpretation_sowilo_upright
  Rune.TIWAZ -> Res.string.interpretation_tiwaz_upright
  Rune.BERKANO -> Res.string.interpretation_berkano_upright
  Rune.EHWAZ -> Res.string.interpretation_ehwaz_upright
  Rune.MANNAZ -> Res.string.interpretation_mannaz_upright
  Rune.LAGUZ -> Res.string.interpretation_laguz_upright
  Rune.INGWAZ -> Res.string.interpretation_ingwaz_upright
  Rune.DAGAZ -> Res.string.interpretation_dagaz_upright
  Rune.OTHALA -> Res.string.interpretation_othala_upright
}

private fun reversedInterpretation(rune: Rune): StringResource = when (rune) {
  Rune.FEHU -> Res.string.interpretation_fehu_reversed
  Rune.URUZ -> Res.string.interpretation_uruz_reversed
  Rune.THURISAZ -> Res.string.interpretation_thurisaz_reversed
  Rune.ANSUZ -> Res.string.interpretation_ansuz_reversed
  Rune.RAIDHO -> Res.string.interpretation_raidho_reversed
  Rune.KENAZ -> Res.string.interpretation_kenaz_reversed
  //  Rune.GEBO not reversible
  Rune.WUNJO -> Res.string.interpretation_wunjo_reversed
  Rune.HAGALAZ -> Res.string.interpretation_hagalaz_reversed
  Rune.NAUTHIZ -> Res.string.interpretation_nauthiz_reversed
  //  Rune.ISA not reversible
  //  Rune.JERA not reversible
  //  Rune.EIHWAZ not reversible
  Rune.PERTHRO -> Res.string.interpretation_perthro_reversed
  Rune.ALGIZ -> Res.string.interpretation_algiz_reversed
  //  Rune.SOWILO not reversible
  Rune.TIWAZ -> Res.string.interpretation_tiwaz_reversed
  Rune.BERKANO -> Res.string.interpretation_berkano_reversed
  Rune.EHWAZ -> Res.string.interpretation_ehwaz_reversed
  Rune.MANNAZ -> Res.string.interpretation_mannaz_reversed
  Rune.LAGUZ -> Res.string.interpretation_laguz_reversed
  //  Rune.INGWAZ not reversible
  //  Rune.DAGAZ not reversible
  Rune.OTHALA -> Res.string.interpretation_othala_reversed
  else -> error("Unknown reversed rune: $rune")
}

// fetches contain supplemental text to the general interpretation (for certain rune/orientation/category combinations) if found
fun DrawnRune.supplementalInterpretation(readingCategory: ReadingCategory): StringResource? {
  if (readingCategory == ReadingCategory.GENERAL) return null
  val key = "interpretation_${rune.key}_${orientation.key}_${readingCategory.key}"
  return Res.allStringResources[key]
}
