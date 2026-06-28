package com.taka.runejournal.feature.reading.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface ReadingRoute : NavKey

@Serializable
data object ReadingStartRoute : ReadingRoute

@Serializable
data object ReadingDrawRoute : ReadingRoute