package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.ui.theme.Ash
import com.taka.runejournal.core.ui.theme.Bone
import com.taka.runejournal.core.ui.theme.Clay
import com.taka.runejournal.core.ui.theme.ErrorBlush
import com.taka.runejournal.core.ui.theme.ErrorInk
import com.taka.runejournal.core.ui.theme.ErrorRed
import com.taka.runejournal.core.ui.theme.Ink
import com.taka.runejournal.core.ui.theme.LightClay
import com.taka.runejournal.core.ui.theme.Moss
import com.taka.runejournal.core.ui.theme.PaleMoss
import com.taka.runejournal.core.ui.theme.Parchment
import com.taka.runejournal.core.ui.theme.Sand
import com.taka.runejournal.core.ui.theme.TakaTheme
import com.taka.runejournal.core.ui.theme.Walnut
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun DesignPlaygroundPreview() {
  TakaTheme {
    DesignPlayground()
  }
}

@Composable
fun DesignPlayground(
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier,
    color = MaterialTheme.colorScheme.background,
    contentColor = MaterialTheme.colorScheme.onBackground,
  ) {
    Column(
      modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(24.dp),
      verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
      Text(
        text = "Taka Design Playground",
        style = MaterialTheme.typography.headlineMedium,
      )

      Text(
        text = "A preview surface for checking theme colors, typography, shapes, and common Material components.",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )

      SectionTitle("Named palette")
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        ColorSwatch("Parchment", Parchment, Ink)
        ColorSwatch("Bone", Bone, Ink)
        ColorSwatch("Walnut", Walnut, Bone)
        ColorSwatch("Ash", Ash, Bone)
        ColorSwatch("Ink", Ink, Bone)
        ColorSwatch("Clay", Clay, Ink)
        ColorSwatch("Moss", Moss, Bone)
        ColorSwatch("PaleMoss", PaleMoss, Ink)
        ColorSwatch("Sand", Sand, Ink)
        ColorSwatch("LightClay", LightClay, Ink)
        ColorSwatch("ErrorRed", ErrorRed, Color.White)
        ColorSwatch("ErrorBlush", ErrorBlush, ErrorInk)
        ColorSwatch("ErrorInk", ErrorInk, ErrorBlush)
      }

      SectionTitle("Material color roles")
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        val colors = MaterialTheme.colorScheme

        ColorRoleSwatch("primary", colors.primary, colors.onPrimary)
        ColorRoleSwatch("primaryContainer", colors.primaryContainer, colors.onPrimaryContainer)

        ColorRoleSwatch("secondary", colors.secondary, colors.onSecondary)
        ColorRoleSwatch("secondaryContainer", colors.secondaryContainer, colors.onSecondaryContainer)

        ColorRoleSwatch("tertiary", colors.tertiary, colors.onTertiary)
        ColorRoleSwatch("tertiaryContainer", colors.tertiaryContainer, colors.onTertiaryContainer)

        ColorRoleSwatch("background", colors.background, colors.onBackground)
        ColorRoleSwatch("surface", colors.surface, colors.onSurface)
        ColorRoleSwatch("surfaceVariant", colors.surfaceVariant, colors.onSurfaceVariant)

        ColorRoleSwatch("outline", colors.outline, colors.onSurface)
        ColorRoleSwatch("outlineVariant", colors.outlineVariant, colors.onSurface)

        ColorRoleSwatch("error", colors.error, colors.onError)
        ColorRoleSwatch("errorContainer", colors.errorContainer, colors.onErrorContainer)
      }

      SectionTitle("Typography")
      TypographySamples(MaterialTheme.typography)

      SectionTitle("Shapes")
      ShapeSamples()

      SectionTitle("Components")
      ComponentSamples()
    }
  }
}

@Composable
private fun SectionTitle(text: String) {
  Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    Text(
      text = text,
      style = MaterialTheme.typography.titleLarge,
    )
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
  }
}

@Composable
private fun ColorSwatch(
  name: String,
  color: Color,
  contentColor: Color,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.width(132.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(72.dp)
        .clip(MaterialTheme.shapes.medium)
        .background(color)
        .border(
          width = 1.dp,
          color = MaterialTheme.colorScheme.outlineVariant,
          shape = MaterialTheme.shapes.medium,
        )
        .padding(12.dp),
    ) {
      Text(
        text = name,
        style = MaterialTheme.typography.labelMedium,
        color = contentColor,
      )
    }

    Text(
      text = name,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}

@Composable
private fun ColorRoleSwatch(
  roleName: String,
  color: Color,
  contentColor: Color,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier.width(180.dp),
    shape = MaterialTheme.shapes.medium,
    color = color,
    contentColor = contentColor,
    tonalElevation = 0.dp,
    shadowElevation = 0.dp,
  ) {
    Column(
      modifier = Modifier.padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Text(
        text = roleName,
        style = MaterialTheme.typography.labelLarge,
      )
      Text(
        text = "The quick brown fox",
        style = MaterialTheme.typography.bodySmall,
      )
    }
  }
}

@Composable
private fun TypographySamples(
  typography: Typography,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.horizontalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    TypographySample("displayLarge", typography.displayLarge)
    TypographySample("displayMedium", typography.displayMedium)
    TypographySample("displaySmall", typography.displaySmall)

    TypographySample("headlineLarge", typography.headlineLarge)
    TypographySample("headlineMedium", typography.headlineMedium)
    TypographySample("headlineSmall", typography.headlineSmall)

    TypographySample("titleLarge", typography.titleLarge)
    TypographySample("titleMedium", typography.titleMedium)
    TypographySample("titleSmall", typography.titleSmall)

    TypographySample("bodyLarge", typography.bodyLarge)
    TypographySample("bodyMedium", typography.bodyMedium)
    TypographySample("bodySmall", typography.bodySmall)

    TypographySample("labelLarge", typography.labelLarge)
    TypographySample("labelMedium", typography.labelMedium)
    TypographySample("labelSmall", typography.labelSmall)
  }
}

@Composable
private fun TypographySample(
  name: String,
  style: TextStyle,
) {
  Column {
    Text(
      text = name,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Text(
      text = "Taka Rune Journal",
      style = style,
      color = MaterialTheme.colorScheme.onSurface,
    )
  }
}

@Composable
private fun ShapeSamples() {
  Row(
    modifier = Modifier.horizontalScroll(rememberScrollState()),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    ShapeSample("extraSmall", MaterialTheme.shapes.extraSmall)
    ShapeSample("small", MaterialTheme.shapes.small)
    ShapeSample("medium", MaterialTheme.shapes.medium)
    ShapeSample("large", MaterialTheme.shapes.large)
    ShapeSample("extraLarge", MaterialTheme.shapes.extraLarge)
    ShapeSample("circle", CircleShape)
  }
}

@Composable
private fun ShapeSample(
  name: String,
  shape: androidx.compose.ui.graphics.Shape,
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Box(
      modifier = Modifier
        .size(88.dp)
        .clip(shape)
        .background(MaterialTheme.colorScheme.primaryContainer)
        .border(
          width = 1.dp,
          color = MaterialTheme.colorScheme.outline,
          shape = shape,
        ),
    )
    Text(
      text = name,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}

@Composable
private fun ComponentSamples() {
  Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
    Card {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Text(
          text = "Card",
          style = MaterialTheme.typography.titleMedium,
        )
        Text(
          text = "A calm surface for readings, journal entries, prompts, and reflective content.",
          style = MaterialTheme.typography.bodyMedium,
        )
      }
    }

    OutlinedCard {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Text(
          text = "Outlined card",
          style = MaterialTheme.typography.titleMedium,
        )
        Text(
          text = "Useful when you want a quieter container with a visible boundary.",
          style = MaterialTheme.typography.bodyMedium,
        )
      }
    }

    Row(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      Button(onClick = {}) {
        Text("Primary")
      }

      OutlinedButton(onClick = {}) {
        Text("Outlined")
      }

      TextButton(onClick = {}) {
        Text("Text")
      }
    }

    Row(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      ElevatedButton(onClick = {}) {
        Text("Elevated")
      }
    }

    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      AssistChip(
        onClick = {},
        label = { Text("Self") },
      )
      FilterChip(
        selected = true,
        onClick = {},
        label = { Text("Purpose") },
      )
      SuggestionChip(
        onClick = {},
        label = { Text("Crossroads") },
      )
    }

    Surface(
      shape = MaterialTheme.shapes.large,
      color = MaterialTheme.colorScheme.errorContainer,
      contentColor = MaterialTheme.colorScheme.onErrorContainer,
    ) {
      Text(
        text = "Error container sample",
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.bodyMedium,
      )
    }

    Spacer(modifier = Modifier.height(8.dp))
  }
}