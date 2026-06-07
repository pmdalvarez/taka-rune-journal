package com.taka.runejournal

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.navigation.AppNavDisplay
import com.taka.runejournal.core.ui.theme.TakaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    TakaTheme {
        AppNavDisplay(modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing))
    }
}
