package com.taka.runejournal.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.newsreader_medium

// Font(resource) is composable in Compose Multiplatform, so the font family and typography are built in composition
@Composable
private fun serifFontFamily() = FontFamily(
  Font(Res.font.newsreader_medium, FontWeight.Medium),
)

/**
 * Serif for display, headline and large title text, to give headings character.
 * All other styles keep Material's defaults and the system font.
 */
@Composable
fun takaTypography(): Typography {
  val serif = serifFontFamily()
  val default = Typography()

  return default.copy(
    displayLarge = default.displayLarge.copy(fontFamily = serif, fontWeight = FontWeight.Medium),
    displayMedium = default.displayMedium.copy(fontFamily = serif, fontWeight = FontWeight.Medium),
    displaySmall = default.displaySmall.copy(fontFamily = serif, fontWeight = FontWeight.Medium),
    headlineLarge = default.headlineLarge.copy(fontFamily = serif, fontWeight = FontWeight.Medium),
    headlineMedium = default.headlineMedium.copy(fontFamily = serif, fontWeight = FontWeight.Medium),
    headlineSmall = default.headlineSmall.copy(fontFamily = serif, fontWeight = FontWeight.Medium),
    titleLarge = default.titleLarge.copy(fontFamily = serif, fontWeight = FontWeight.Medium),
  )
}
