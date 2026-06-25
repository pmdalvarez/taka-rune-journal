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