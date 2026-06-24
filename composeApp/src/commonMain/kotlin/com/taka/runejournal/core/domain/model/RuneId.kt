package com.taka.runejournal.core.domain.model

enum class RuneId(
    val key: String, // Changing key values can break existing references in db or string files
    val glyph: String,
    val displayName: String,
) {
    FEHU(
        key = "fehu",
        glyph = "ᚠ",
        displayName = "Fehu",
    ),
    URUZ(
        key = "uruz",
        glyph = "ᚢ",
        displayName = "Uruz",
    ),
    THURISAZ(
        key = "thurisaz",
        glyph = "ᚦ",
        displayName = "Thurisaz",
    ),
    ANSUZ(
        key = "ansuz",
        glyph = "ᚨ",
        displayName = "Ansuz",
    ),
    RAIDHO(
        key = "raidho",
        glyph = "ᚱ",
        displayName = "Raidho",
    ),
    KENAZ(
        key = "kenaz",
        glyph = "ᚲ",
        displayName = "Kenaz",
    ),
    GEBO(
        key = "gebo",
        glyph = "ᚷ",
        displayName = "Gebo",
    ),
    WUNJO(
        key = "wunjo",
        glyph = "ᚹ",
        displayName = "Wunjo",
    ),
    HAGALAZ(
        key = "hagalaz",
        glyph = "ᚺ",
        displayName = "Hagalaz",
    ),
    NAUTHIZ(
        key = "nauthiz",
        glyph = "ᚾ",
        displayName = "Nauthiz",
    ),
    ISA(
        key = "isa",
        glyph = "ᛁ",
        displayName = "Isa",
    ),
    JERA(
        key = "jera",
        glyph = "ᛃ",
        displayName = "Jera",
    ),
    EIHWAZ(
        key = "eihwaz",
        glyph = "ᛇ",
        displayName = "Eihwaz",
    ),
    PERTHRO(
        key = "perthro",
        glyph = "ᛈ",
        displayName = "Perthro",
    ),
    ALGIZ(
        key = "algiz",
        glyph = "ᛉ",
        displayName = "Algiz",
    ),
    SOWILO(
        key = "sowilo",
        glyph = "ᛋ",
        displayName = "Sowilo",
    ),
    TIWAZ(
        key = "tiwaz",
        glyph = "ᛏ",
        displayName = "Tiwaz",
    ),
    BERKANO(
        key = "berkano",
        glyph = "ᛒ",
        displayName = "Berkano",
    ),
    EHWAZ(
        key = "ehwaz",
        glyph = "ᛖ",
        displayName = "Ehwaz",
    ),
    MANNAZ(
        key = "mannaz",
        glyph = "ᛗ",
        displayName = "Mannaz",
    ),
    LAGUZ(
        key = "laguz",
        glyph = "ᛚ",
        displayName = "Laguz",
    ),
    INGWAZ(
        key = "ingwaz",
        glyph = "ᛜ",
        displayName = "Ingwaz",
    ),
    DAGAZ(
        key = "dagaz",
        glyph = "ᛞ",
        displayName = "Dagaz",
    ),
    OTHALA(
        key = "othala",
        glyph = "ᛟ",
        displayName = "Othala",
    );

    companion object {
        fun fromKey(key: String): RuneId? =
            entries.firstOrNull { it.key == key }
    }
}