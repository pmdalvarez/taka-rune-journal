package com.taka.runejournal

import androidx.compose.runtime.*
import com.taka.runejournal.core.navigation.AppNavHost
import com.taka.runejournal.core.ui.theme.TakaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    TakaTheme {
        AppNavHost()
    }
}
