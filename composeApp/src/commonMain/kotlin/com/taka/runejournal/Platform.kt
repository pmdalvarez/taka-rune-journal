package com.taka.runejournal

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform