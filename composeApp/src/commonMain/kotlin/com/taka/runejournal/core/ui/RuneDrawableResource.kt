package com.taka.runejournal.core.ui

import com.taka.runejournal.core.domain.model.RuneId
import org.jetbrains.compose.resources.DrawableResource
import taka_rune_journal.composeapp.generated.resources.*

fun RuneId.drawable(): DrawableResource =
  when (this) {
    RuneId.FEHU -> Res.drawable.rune_fehu
    RuneId.URUZ -> Res.drawable.rune_uruz
    RuneId.THURISAZ -> Res.drawable.rune_thurisaz
    RuneId.ANSUZ -> Res.drawable.rune_ansuz
    RuneId.RAIDHO -> Res.drawable.rune_raidho
    RuneId.KENAZ -> Res.drawable.rune_kenaz
    RuneId.GEBO -> Res.drawable.rune_gebo
    RuneId.WUNJO -> Res.drawable.rune_wunjo
    RuneId.HAGALAZ -> Res.drawable.rune_hagalaz
    RuneId.NAUTHIZ -> Res.drawable.rune_nauthiz
    RuneId.ISA -> Res.drawable.rune_isa
    RuneId.JERA -> Res.drawable.rune_jera
    RuneId.EIHWAZ -> Res.drawable.rune_eihwaz
    RuneId.PERTHRO -> Res.drawable.rune_perthro
    RuneId.ALGIZ -> Res.drawable.rune_algiz
    RuneId.SOWILO -> Res.drawable.rune_sowilo
    RuneId.TIWAZ -> Res.drawable.rune_tiwaz
    RuneId.BERKANO -> Res.drawable.rune_berkano
    RuneId.EHWAZ -> Res.drawable.rune_ehwaz
    RuneId.MANNAZ -> Res.drawable.rune_mannaz
    RuneId.LAGUZ -> Res.drawable.rune_laguz
    RuneId.INGWAZ -> Res.drawable.rune_ingwaz
    RuneId.DAGAZ -> Res.drawable.rune_dagaz
    RuneId.OTHALA -> Res.drawable.rune_othala
  }