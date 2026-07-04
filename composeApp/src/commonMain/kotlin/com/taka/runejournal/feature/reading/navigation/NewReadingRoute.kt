package com.taka.runejournal.feature.reading.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface NewReadingRoute : NavKey

@Serializable
data object NewReadingStartRoute : NewReadingRoute

@Serializable
data object NewReadingDrawRoute : NewReadingRoute