package com.taka.runejournal.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey

@Serializable
data object TimelineRoute : AppRoute

@Serializable
data class TimelineDetailRoute(
    val timelineItemId: Long,
) : AppRoute

@Serializable
data object NewJournalEntryRoute : AppRoute

@Serializable
data object ReadingFlowRoute : AppRoute

@Serializable
data object SettingsRoute : AppRoute


@Serializable
data object AboutRoute : AppRoute
