package com.taka.runejournal.core.domain.model

enum class RuneId(
    val key: String, // Changing key values can break existing references in db or string files
) {
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