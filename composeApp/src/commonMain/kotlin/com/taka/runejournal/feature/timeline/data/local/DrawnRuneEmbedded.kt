package com.taka.runejournal.feature.timeline.data.local

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.RuneId
import com.taka.runejournal.core.domain.model.RuneOrientation

data class DrawnRuneEmbedded(
  val id: RuneId,
  val orientation: RuneOrientation,
)

fun DrawnRuneEmbedded.toDomain(): DrawnRune = DrawnRune(id = id, orientation = orientation)

fun DrawnRune.toEmbedded(): DrawnRuneEmbedded = DrawnRuneEmbedded(id = id, orientation = orientation)