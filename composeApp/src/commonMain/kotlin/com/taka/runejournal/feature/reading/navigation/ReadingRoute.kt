package com.taka.runejournal.feature.reading.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface ReadingRoute : NavKey

@Serializable
data object NewReadingStartRoute : ReadingRoute

@Serializable
data object NewReadingDrawRoute : ReadingRoute

@Serializable
data object NewReadingInterpretationRoute : ReadingRoute