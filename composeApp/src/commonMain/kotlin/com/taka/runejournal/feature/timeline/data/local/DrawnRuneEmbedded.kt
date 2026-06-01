package com.taka.runejournal.feature.timeline.data.local

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.RuneId
import com.taka.runejournal.core.domain.model.RuneOrientation

data class DrawnRuneEmbedded(
  val id: String,
  val orientation: String,
)

fun DrawnRuneEmbedded.toDomain(): DrawnRune =
  DrawnRune(
    id = RuneId.fromKey(id)
      ?: error("Unknown rune id: $id"),
    orientation = RuneOrientation.fromKey(orientation)
      ?: error("Unknown rune orientation: $orientation")
  )

fun DrawnRune.toEmbedded(): DrawnRuneEmbedded = DrawnRuneEmbedded(id = id.key, orientation = orientation.key)