package com.taka.runejournal.core.domain.model

import org.jetbrains.compose.resources.DrawableResource
import taka_rune_journal.composeapp.generated.resources.*

enum class RuneId(
    val key: String,
    val drawable: DrawableResource,
) {
    FEHU("fehu", Res.drawable.rune_fehu),
    URUZ("uruz", Res.drawable.rune_uruz),
    THURISAZ("thurisaz", Res.drawable.rune_thurisaz),
    ANSUZ("ansuz", Res.drawable.rune_ansuz),
    RAIDHO("raidho", Res.drawable.rune_raidho),
    KENAZ("kenaz", Res.drawable.rune_kenaz),
    GEBO("gebo", Res.drawable.rune_gebo),
    WUNJO("wunjo", Res.drawable.rune_wunjo),
    HAGALAZ("hagalaz", Res.drawable.rune_hagalaz),
    NAUTHIZ("nauthiz", Res.drawable.rune_nauthiz),
    ISA("isa", Res.drawable.rune_isa),
    JERA("jera", Res.drawable.rune_jera),
    EIHWAZ("eihwaz", Res.drawable.rune_eihwaz),
    PERTHRO("perthro", Res.drawable.rune_perthro),
    ALGIZ("algiz", Res.drawable.rune_algiz),
    SOWILO("sowilo", Res.drawable.rune_sowilo),
    TIWAZ("tiwaz", Res.drawable.rune_tiwaz),
    BERKANO("berkano", Res.drawable.rune_berkano),
    EHWAZ("ehwaz", Res.drawable.rune_ehwaz),
    MANNAZ("mannaz", Res.drawable.rune_mannaz),
    LAGUZ("laguz", Res.drawable.rune_laguz),
    INGWAZ("ingwaz", Res.drawable.rune_ingwaz),
    DAGAZ("dagaz", Res.drawable.rune_dagaz),
    OTHALA("othala", Res.drawable.rune_othala);

    companion object {
        fun fromKey(key: String): RuneId? =
            entries.firstOrNull { it.key == key }
    }
}