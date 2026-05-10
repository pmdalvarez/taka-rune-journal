package com.taka.runejournal.core.model

enum class RuneId(val key: String) {
    FEHU("fehu"),
    URUZ("uruz"),
    THURISAZ("thurisaz"),
    ANSUZ("ansuz"),
    RAIDHO("raidho"),
    KENAZ("kenaz"),
    GEBO("gebo"),
    WUNJO("wunjo"),
    HAGALAZ("hagalaz"),
    NAUTHIZ("nauthiz"),
    ISA("isa"),
    JERA("jera"),
    EIHWAZ("eihwaz"),
    PERTHRO("perthro"),
    ALGIZ("algiz"),
    SOWILO("sowilo"),
    TIWAZ("tiwaz"),
    BERKANO("berkano"),
    EHWAZ("ehwaz"),
    MANNAZ("mannaz"),
    LAGUZ("laguz"),
    INGWAZ("ingwaz"),
    DAGAZ("dagaz"),
    OTHALA("othala");

    companion object {
        fun fromKey(key: String): RuneId? =
            entries.firstOrNull { it.key == key }
    }
}