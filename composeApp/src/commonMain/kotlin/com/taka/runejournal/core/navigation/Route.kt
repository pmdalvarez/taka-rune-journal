package com.taka.runejournal.core.navigation

sealed class Route(val value: String) {
    data object Timeline : Route("timeline")
    data object TimelineDetail : Route("timeline/detail")
    data object EditTimelineItem : Route("edit_timeline_item")
    data object NewJournalEntry : Route("timeline/new_journal_entry")
    data object NewReadingGraph : Route("new_reading")
    data object NewReadingStart : Route("new_reading/start")
    data object NewReadingDraw : Route("new_reading/draw")
    data object NewReadingInterpretation : Route("new_reading/interpretation")

    data object Settings : Route("settings")
    data object About : Route("about") // TODO remove
}