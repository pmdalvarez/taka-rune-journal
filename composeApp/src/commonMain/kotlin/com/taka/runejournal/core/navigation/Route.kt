package com.taka.runejournal.core.navigation

sealed class Route(val value: String) {
    data object Home : Route("home")
    data object About : Route("about")
}