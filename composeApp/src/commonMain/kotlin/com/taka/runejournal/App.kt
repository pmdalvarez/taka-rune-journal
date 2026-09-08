package com.taka.runejournal

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.navigation.AppNavDisplay
import com.taka.runejournal.core.ui.theme.TakaTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock

@Composable
@Preview
fun App() {
    // TODO - remove for production version
    if (Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date >= LocalDate(2026, 10, 1)) {
        Text("This test version has expired.")
        return
    }
    TakaTheme {
        AppNavDisplay(modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing))
    }
}
