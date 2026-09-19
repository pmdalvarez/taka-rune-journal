package com.taka.runejournal.core.navigation

import androidx.navigation3.runtime.NavKey
import com.taka.runejournal.core.domain.model.Rune
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey

@Serializable
data object TimelineRoute : AppRoute

@Serializable
data class JournalEntryDetailRoute(
    val id: Long,
) : AppRoute

@Serializable
data class ReadingInterpretationRoute(
    val id: Long,
) : AppRoute

@Serializable
data object NewJournalEntryRoute : AppRoute

@Serializable
data object NewReadingFlowRoute : AppRoute

@Serializable
data object SettingsRoute : AppRoute

@Serializable
data object AboutRoute : AppRoute

@Serializable
data object GlossaryRoute : AppRoute

@Serializable
data class RuneDetailRoute(
    val rune: Rune,
) : AppRoute


@Serializable
data object DesignSystemRoute : AppRoute