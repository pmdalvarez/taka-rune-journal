package com.taka.runejournal

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.taka.runejournal.core.domain.model.ThemeMode
import com.taka.runejournal.core.domain.repository.SettingsRepository
import com.taka.runejournal.core.navigation.AppNavDisplay
import com.taka.runejournal.core.ui.SystemBarIconTheme
import com.taka.runejournal.core.ui.theme.TakaTheme
import com.taka.runejournal.core.ui.theme.isAppInDarkTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.koinInject
import kotlin.time.Clock

@Composable
@Preview
fun App() {
    // TODO - remove for production version
    if (Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date >= LocalDate(2026, 11, 1)) {
        Text("This test version has expired.")
        return
    }

    val settingsRepository = koinInject<SettingsRepository>()
    val themeMode by settingsRepository.themeMode.collectAsState(initial = ThemeMode.SYSTEM)
    TakaTheme(themeMode = themeMode) {
        SystemBarIconTheme(darkTheme = isAppInDarkTheme())
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background, // fills the status/navigation bars with the correct dark/light theme background
        ) {
            AppNavDisplay(modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing))
        }
    }
}
