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
import taka_rune_journal.composeapp.generated.resources.origin_algiz
import taka_rune_journal.composeapp.generated.resources.origin_ansuz
import taka_rune_journal.composeapp.generated.resources.origin_berkano
import taka_rune_journal.composeapp.generated.resources.origin_dagaz
import taka_rune_journal.composeapp.generated.resources.origin_ehwaz
import taka_rune_journal.composeapp.generated.resources.origin_eihwaz
import taka_rune_journal.composeapp.generated.resources.origin_fehu
import taka_rune_journal.composeapp.generated.resources.origin_gebo
import taka_rune_journal.composeapp.generated.resources.origin_hagalaz
import taka_rune_journal.composeapp.generated.resources.origin_ingwaz
import taka_rune_journal.composeapp.generated.resources.origin_isa
import taka_rune_journal.composeapp.generated.resources.origin_jera
import taka_rune_journal.composeapp.generated.resources.origin_kenaz
import taka_rune_journal.composeapp.generated.resources.origin_laguz
import taka_rune_journal.composeapp.generated.resources.origin_mannaz
import taka_rune_journal.composeapp.generated.resources.origin_nauthiz
import taka_rune_journal.composeapp.generated.resources.origin_othala
import taka_rune_journal.composeapp.generated.resources.origin_perthro
import taka_rune_journal.composeapp.generated.resources.origin_raidho
import taka_rune_journal.composeapp.generated.resources.origin_sowilo
import taka_rune_journal.composeapp.generated.resources.origin_thurisaz
import taka_rune_journal.composeapp.generated.resources.origin_tiwaz
import taka_rune_journal.composeapp.generated.resources.origin_uruz
import taka_rune_journal.composeapp.generated.resources.origin_wunjo

fun Rune.origin(): StringResource =
  when (this) {
    Rune.FEHU -> Res.string.origin_fehu
    Rune.URUZ -> Res.string.origin_uruz
    Rune.THURISAZ -> Res.string.origin_thurisaz
    Rune.ANSUZ -> Res.string.origin_ansuz
    Rune.RAIDHO -> Res.string.origin_raidho
    Rune.KENAZ -> Res.string.origin_kenaz
    Rune.GEBO -> Res.string.origin_gebo
    Rune.WUNJO -> Res.string.origin_wunjo
    Rune.HAGALAZ -> Res.string.origin_hagalaz
    Rune.NAUTHIZ -> Res.string.origin_nauthiz
    Rune.ISA -> Res.string.origin_isa
    Rune.JERA -> Res.string.origin_jera
    Rune.EIHWAZ -> Res.string.origin_eihwaz
    Rune.PERTHRO -> Res.string.origin_perthro
    Rune.ALGIZ -> Res.string.origin_algiz
    Rune.SOWILO -> Res.string.origin_sowilo
    Rune.TIWAZ -> Res.string.origin_tiwaz
    Rune.BERKANO -> Res.string.origin_berkano
    Rune.EHWAZ -> Res.string.origin_ehwaz
    Rune.MANNAZ -> Res.string.origin_mannaz
    Rune.LAGUZ -> Res.string.origin_laguz
    Rune.INGWAZ -> Res.string.origin_ingwaz
    Rune.DAGAZ -> Res.string.origin_dagaz
    Rune.OTHALA -> Res.string.origin_othala
  }


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
