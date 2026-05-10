package com.example.kmpstarter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform