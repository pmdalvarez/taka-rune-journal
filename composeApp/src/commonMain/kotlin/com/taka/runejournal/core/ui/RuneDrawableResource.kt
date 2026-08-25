package com.taka.runejournal.core.ui

import com.taka.runejournal.core.domain.model.Rune
import org.jetbrains.compose.resources.DrawableResource
import taka_rune_journal.composeapp.generated.resources.*

fun Rune.drawable(): DrawableResource =
  when (this) {
    Rune.FEHU -> Res.drawable.rune_fehu
    Rune.URUZ -> Res.drawable.rune_uruz
    Rune.THURISAZ -> Res.drawable.rune_thurisaz
    Rune.ANSUZ -> Res.drawable.rune_ansuz
    Rune.RAIDHO -> Res.drawable.rune_raidho
    Rune.KENAZ -> Res.drawable.rune_kenaz
    Rune.GEBO -> Res.drawable.rune_gebo
    Rune.WUNJO -> Res.drawable.rune_wunjo
    Rune.HAGALAZ -> Res.drawable.rune_hagalaz
    Rune.NAUTHIZ -> Res.drawable.rune_nauthiz
    Rune.ISA -> Res.drawable.rune_isa
    Rune.JERA -> Res.drawable.rune_jera
    Rune.EIHWAZ -> Res.drawable.rune_eihwaz
    Rune.PERTHRO -> Res.drawable.rune_perthro
    Rune.ALGIZ -> Res.drawable.rune_algiz
    Rune.SOWILO -> Res.drawable.rune_sowilo
    Rune.TIWAZ -> Res.drawable.rune_tiwaz
    Rune.BERKANO -> Res.drawable.rune_berkano
    Rune.EHWAZ -> Res.drawable.rune_ehwaz
    Rune.MANNAZ -> Res.drawable.rune_mannaz
    Rune.LAGUZ -> Res.drawable.rune_laguz
    Rune.INGWAZ -> Res.drawable.rune_ingwaz
    Rune.DAGAZ -> Res.drawable.rune_dagaz
    Rune.OTHALA -> Res.drawable.rune_othala
  }

fun Rune.glowingDrawable(): DrawableResource =
  when (this) {
    Rune.FEHU -> Res.drawable.rune_fehu_glowing
    Rune.URUZ -> Res.drawable.rune_uruz_glowing
    Rune.THURISAZ -> Res.drawable.rune_thurisaz_glowing
    Rune.ANSUZ -> Res.drawable.rune_ansuz_glowing
    Rune.RAIDHO -> Res.drawable.rune_raidho_glowing
    Rune.KENAZ -> Res.drawable.rune_kenaz_glowing
    Rune.GEBO -> Res.drawable.rune_gebo_glowing
    Rune.WUNJO -> Res.drawable.rune_wunjo_glowing
    Rune.HAGALAZ -> Res.drawable.rune_hagalaz_glowing
    Rune.NAUTHIZ -> Res.drawable.rune_nauthiz_glowing
    Rune.ISA -> Res.drawable.rune_isa_glowing
    Rune.JERA -> Res.drawable.rune_jera_glowing
    Rune.EIHWAZ -> Res.drawable.rune_eihwaz_glowing
    Rune.PERTHRO -> Res.drawable.rune_perthro_glowing
    Rune.ALGIZ -> Res.drawable.rune_algiz_glowing
    Rune.SOWILO -> Res.drawable.rune_sowilo_glowing
    Rune.TIWAZ -> Res.drawable.rune_tiwaz_glowing
    Rune.BERKANO -> Res.drawable.rune_berkano_glowing
    Rune.EHWAZ -> Res.drawable.rune_ehwaz_glowing
    Rune.MANNAZ -> Res.drawable.rune_mannaz_glowing
    Rune.LAGUZ -> Res.drawable.rune_laguz_glowing
    Rune.INGWAZ -> Res.drawable.rune_ingwaz_glowing
    Rune.DAGAZ -> Res.drawable.rune_dagaz_glowing
    Rune.OTHALA -> Res.drawable.rune_othala_glowing
  }