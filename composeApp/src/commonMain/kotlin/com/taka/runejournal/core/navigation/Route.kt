package com.taka.runejournal.core.navigation

sealed class Route(val value: String) {
    data object Timeline : Route("timeline")
    data object TimelineDetail : Route("timeline_detail")
    data object EditTimelineItem : Route("edit_timeline_item")
    data object NewJournalEntry : Route("new_journal_entry")
    data object NewReadingStart : Route("new_reading_start")
    data object NewReadingDraw : Route("new_reading_draw")
    data object NewReadingInterpretation : Route("new_reading_interpretation")
    data object Settings : Route("settings")
    data object About : Route("about") // TODO remove
}