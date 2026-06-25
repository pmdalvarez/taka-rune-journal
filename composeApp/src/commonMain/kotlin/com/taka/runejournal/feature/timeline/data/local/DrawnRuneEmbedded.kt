package com.taka.runejournal.feature.timeline.data.local

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.core.domain.model.RuneOrientation

data class DrawnRuneEmbedded(
  val runeKey: String,
  val orientationKey: String,
)

fun DrawnRuneEmbedded.toDomain(): DrawnRune =
  DrawnRune(
    rune = Rune.fromKey(runeKey)
      ?: error("Unknown rune id: $runeKey"),
    orientation = RuneOrientation.fromKey(orientationKey)
      ?: error("Unknown rune orientation: $orientationKey")
  )

fun DrawnRune.toEmbedded(): DrawnRuneEmbedded = DrawnRuneEmbedded(runeKey = rune.key, orientationKey = orientation.key)