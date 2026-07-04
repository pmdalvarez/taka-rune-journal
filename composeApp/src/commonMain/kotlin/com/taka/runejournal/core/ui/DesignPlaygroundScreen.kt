package com.taka.runejournal.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.core.ui.components.ButtonStyle
import com.taka.runejournal.core.ui.components.TakaButton
import com.taka.runejournal.core.ui.components.TakaTopBar
import com.taka.runejournal.core.ui.components.TakaTopBarNavigationIcon
import com.taka.runejournal.core.ui.theme.TakaTheme
import org.jetbrains.compose.resources.painterResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.ic_topbar_icon
import taka_rune_journal.composeapp.generated.resources.rune_empty
import kotlin.collections.chunked
import kotlin.collections.forEach

@Preview(
  showBackground = true,
  heightDp = 200000,           // ← Force the preview to be tall
  widthDp = 500
)
@Composable
fun DesignPlaygroundPreview() {
  TakaTheme {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())   // ← Scroll lives here in preview
    ) {
      DesignPlayground()
    }
  }}

@Composable
fun DesignPlaygroundScreen(
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      TakaTopBar(
        title = "Design Playground",
        navigationIcon = TakaTopBarNavigationIcon.Back,
        onNavigationClick = onBackClick,
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {
      DesignPlayground()
    }
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
        .fillMaxWidth()
        .padding(24.dp),
      verticalArrangement = Arrangement.spacedBy(28.dp),
    ) {
      HeroSection()

      SectionTitle("Buttons")
      ButtonSamples()

      SectionTitle("Typography")
      TypographySamples(MaterialTheme.typography)

      SectionTitle("Containers")
      ContainerSamples()

      SectionTitle("Cards")
      CardSamples()

      SectionTitle("Chips")
      ChipSamples()

      SectionTitle("Error states")
      ErrorSamples()

      SectionTitle("Surface hierarchy")
      SurfaceHierarchySamples()

      SectionTitle("Shapes")
      ShapeSamples()

      SectionTitle("Raw color role reference")
      ColorRoleReference()

      SectionTitle("Assets")
      RunePreview()
    }
  }
}

@Composable
private fun HeroSection() {
  Surface(
    shape = MaterialTheme.shapes.extraLarge,
    color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor = MaterialTheme.colorScheme.onSurface,
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      Text(
        text = "Taka Design Playground",
        style = MaterialTheme.typography.headlineMedium,
      )

      Text(
        text = "A preview of how the active theme looks in real UI components.",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )

      Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        Button(onClick = {}) {
          Text("Start reading")
        }

        OutlinedButton(onClick = {}) {
          Text("Journal")
        }
      }
    }
  }
}

@Composable
private fun ButtonSamples() {
  Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      TakaButton(onClick = {}) {
        Text("Primary")
      }

      TakaButton(
        onClick = {},
        style = ButtonStyle.Secondary,
      ) {
        Text("Secondary")
      }

      TakaButton(
        onClick = {},
        style = ButtonStyle.Tertiary,
        enabled = false,
      ) {
        Text("Tertiary")
      }

      TakaButton(
        onClick = {},
        enabled = false,
      ) {
        Text("Disabled")
      }
    }

    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      OutlinedButton(onClick = {}) {
        Text("Outlined")
      }

      ElevatedButton(onClick = {}) {
        Text("Elevated")
      }

      TextButton(onClick = {}) {
        Text("Text button")
      }
    }
  }
}

@Composable
private fun TypographySamples(
  typography: Typography,
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    TypographySample("displaySmall", typography.displaySmall, "Runes remember what words forget")
    TypographySample("headlineLarge", typography.headlineLarge, "Today’s reading")
    TypographySample("headlineMedium", typography.headlineMedium, "A quiet signal appears")
    TypographySample("headlineSmall", typography.headlineSmall, "Past · Present · Path")
    TypographySample("titleLarge", typography.titleLarge, "Journal entry")
    TypographySample("titleMedium", typography.titleMedium, "What changed today?")
    TypographySample("titleSmall", typography.titleSmall, "Reflection prompt")
    TypographySample("bodyLarge", typography.bodyLarge, "You pulled a rune that suggests patience, protection, and careful movement.")
    TypographySample("bodyMedium", typography.bodyMedium, "Notice what feels grounded before acting.")
    TypographySample("bodySmall", typography.bodySmall, "Saved just now")
    TypographySample("labelLarge", typography.labelLarge, "PRIMARY ACTION")
    TypographySample("labelMedium", typography.labelMedium, "FILTER CHIP")
    TypographySample("labelSmall", typography.labelSmall, "METADATA")
  }
}

@Composable
private fun TypographySample(
  name: String,
  style: TextStyle,
  text: String,
) {
  Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
    Text(
      text = name,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    Text(
      text = text,
      style = style,
      color = MaterialTheme.colorScheme.onBackground,
    )
  }
}

@Composable
private fun ContainerSamples() {
  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
    RoleContainer(
      label = "primaryContainer / onPrimaryContainer",
      containerColor = MaterialTheme.colorScheme.primaryContainer,
      contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
      body = "Use for important highlighted areas tied to the primary action color.",
    )

    RoleContainer(
      label = "secondaryContainer / onSecondaryContainer",
      containerColor = MaterialTheme.colorScheme.secondaryContainer,
      contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
      body = "Use for supporting information, filters, or quieter callouts.",
    )

    RoleContainer(
      label = "tertiaryContainer / onTertiaryContainer",
      containerColor = MaterialTheme.colorScheme.tertiaryContainer,
      contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
      body = "Use for alternate accents, special states, or warm emphasis.",
    )

    RoleContainer(
      label = "surfaceVariant / onSurfaceVariant",
      containerColor = MaterialTheme.colorScheme.surfaceVariant,
      contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
      body = "Use for subdued panels, dividers, secondary content, or low-emphasis sections.",
    )
  }
}

@Composable
private fun RoleContainer(
  label: String,
  containerColor: Color,
  contentColor: Color,
  body: String,
) {
  Surface(
    modifier = Modifier.fillMaxWidth(),
    shape = MaterialTheme.shapes.large,
    color = containerColor,
    contentColor = contentColor,
  ) {
    Column(
      modifier = Modifier.padding(18.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Text(
        text = label,
        style = MaterialTheme.typography.titleSmall,
      )

      Text(
        text = body,
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}

@Composable
private fun CardSamples() {
  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Card {
      Column(
        modifier = Modifier.padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Text(
          text = "Default card",
          style = MaterialTheme.typography.titleMedium,
        )

        Text(
          text = "A standard surface for timeline entries, rune summaries, and journal previews.",
          style = MaterialTheme.typography.bodyMedium,
        )
      }
    }

    ElevatedCard {
      Column(
        modifier = Modifier.padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Text(
          text = "Elevated card",
          style = MaterialTheme.typography.titleMedium,
        )

        Text(
          text = "Useful when a card needs to feel slightly closer or more active.",
          style = MaterialTheme.typography.bodyMedium,
        )
      }
    }

    OutlinedCard {
      Column(
        modifier = Modifier.padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Text(
          text = "Outlined card",
          style = MaterialTheme.typography.titleMedium,
        )

        Text(
          text = "Useful for quieter sections where the boundary matters more than elevation.",
          style = MaterialTheme.typography.bodyMedium,
        )
      }
    }
  }
}

@Composable
private fun ChipSamples() {
  FlowRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    AssistChip(
      onClick = {},
      label = { Text("Assist") },
    )

    FilterChip(
      selected = true,
      onClick = {},
      label = { Text("Selected") },
    )

    FilterChip(
      selected = false,
      onClick = {},
      label = { Text("Unselected") },
    )

    SuggestionChip(
      onClick = {},
      label = { Text("Suggestion") },
    )
  }
}

@Composable
private fun ErrorSamples() {
  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Surface(
      shape = MaterialTheme.shapes.large,
      color = MaterialTheme.colorScheme.error,
      contentColor = MaterialTheme.colorScheme.onError,
    ) {
      Text(
        text = "error / onError",
        modifier = Modifier.padding(18.dp),
        style = MaterialTheme.typography.titleMedium,
      )
    }

    Surface(
      shape = MaterialTheme.shapes.large,
      color = MaterialTheme.colorScheme.errorContainer,
      contentColor = MaterialTheme.colorScheme.onErrorContainer,
    ) {
      Column(
        modifier = Modifier.padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Text(
          text = "errorContainer / onErrorContainer",
          style = MaterialTheme.typography.titleMedium,
        )

        Text(
          text = "Use this for validation errors, failed saves, destructive confirmations, or warnings.",
          style = MaterialTheme.typography.bodyMedium,
        )
      }
    }
  }
}

@Composable
private fun SurfaceHierarchySamples() {
  val colors = MaterialTheme.colorScheme

  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
    SurfaceRow("background", colors.background, colors.onBackground)
    SurfaceRow("surfaceDim", colors.surfaceDim, colors.onSurface)
    SurfaceRow("surface", colors.surface, colors.onSurface)
    SurfaceRow("surfaceBright", colors.surfaceBright, colors.onSurface)
    SurfaceRow("surfaceContainerLowest", colors.surfaceContainerLowest, colors.onSurface)
    SurfaceRow("surfaceContainerLow", colors.surfaceContainerLow, colors.onSurface)
    SurfaceRow("surfaceContainer", colors.surfaceContainer, colors.onSurface)
    SurfaceRow("surfaceContainerHigh", colors.surfaceContainerHigh, colors.onSurface)
    SurfaceRow("surfaceContainerHighest", colors.surfaceContainerHighest, colors.onSurface)
  }
}

@Composable
private fun SurfaceRow(
  name: String,
  containerColor: Color,
  contentColor: Color,
) {
  Surface(
    modifier = Modifier.fillMaxWidth(),
    shape = MaterialTheme.shapes.medium,
    color = containerColor,
    contentColor = contentColor,
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      Text(
        text = name,
        modifier = Modifier.width(220.dp),
        style = MaterialTheme.typography.labelLarge,
      )

      Text(
        text = "Sample text",
        style = MaterialTheme.typography.bodyMedium,
      )
    }
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
  shape: Shape,
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
private fun ColorRoleReference() {
  val colors = MaterialTheme.colorScheme

  FlowRow(
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    ColorRoleCard("primary", colors.primary, colors.onPrimary)
    ColorRoleCard("onPrimary", colors.onPrimary, colors.primary)

    ColorRoleCard("primaryContainer", colors.primaryContainer, colors.onPrimaryContainer)
    ColorRoleCard("onPrimaryContainer", colors.onPrimaryContainer, colors.primaryContainer)

    ColorRoleCard("secondary", colors.secondary, colors.onSecondary)
    ColorRoleCard("onSecondary", colors.onSecondary, colors.secondary)

    ColorRoleCard("secondaryContainer", colors.secondaryContainer, colors.onSecondaryContainer)
    ColorRoleCard("onSecondaryContainer", colors.onSecondaryContainer, colors.secondaryContainer)

    ColorRoleCard("tertiary", colors.tertiary, colors.onTertiary)
    ColorRoleCard("onTertiary", colors.onTertiary, colors.tertiary)

    ColorRoleCard("tertiaryContainer", colors.tertiaryContainer, colors.onTertiaryContainer)
    ColorRoleCard("onTertiaryContainer", colors.onTertiaryContainer, colors.tertiaryContainer)

    ColorRoleCard("background", colors.background, colors.onBackground)
    ColorRoleCard("onBackground", colors.onBackground, colors.background)

    ColorRoleCard("surface", colors.surface, colors.onSurface)
    ColorRoleCard("onSurface", colors.onSurface, colors.surface)

    ColorRoleCard("surfaceVariant", colors.surfaceVariant, colors.onSurfaceVariant)
    ColorRoleCard("onSurfaceVariant", colors.onSurfaceVariant, colors.surfaceVariant)

    ColorRoleCard("outline", colors.outline, colors.surface)
    ColorRoleCard("outlineVariant", colors.outlineVariant, colors.onSurface)

    ColorRoleCard("inverseSurface", colors.inverseSurface, colors.inverseOnSurface)
    ColorRoleCard("inverseOnSurface", colors.inverseOnSurface, colors.inverseSurface)
    ColorRoleCard("inversePrimary", colors.inversePrimary, colors.onPrimary)

    ColorRoleCard("error", colors.error, colors.onError)
    ColorRoleCard("onError", colors.onError, colors.error)

    ColorRoleCard("errorContainer", colors.errorContainer, colors.onErrorContainer)
    ColorRoleCard("onErrorContainer", colors.onErrorContainer, colors.errorContainer)

    ColorRoleCard("scrim", colors.scrim, colors.onSurface)
  }
}

@Composable
private fun ColorRoleCard(
  name: String,
  containerColor: Color,
  contentColor: Color,
) {
  Surface(
    modifier = Modifier.width(180.dp),
    shape = MaterialTheme.shapes.medium,
    color = containerColor,
    contentColor = contentColor,
  ) {
    Column(
      modifier = Modifier
        .height(84.dp)
        .padding(12.dp),
      verticalArrangement = Arrangement.SpaceBetween,
    ) {
      Text(
        text = name,
        style = MaterialTheme.typography.labelLarge,
      )

      Text(
        text = "Sample",
        style = MaterialTheme.typography.bodySmall,
      )
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

    HorizontalDivider(
      color = MaterialTheme.colorScheme.outlineVariant,
    )
  }
}

@Composable
private fun RunePreview() {
  Column(
    modifier = Modifier
      .background(Color.White)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Image(
          painter = painterResource(Res.drawable.rune_empty),
          contentDescription = "empty",
          modifier = Modifier.size(width = 48.dp, height = 72.dp)
        )

        Text(
          text = "empty",
          modifier = Modifier.padding(top = 4.dp),
          color = Color.Black,
          fontSize = 10.sp
        )
      }
      Column(
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Image(
          painter = painterResource(Res.drawable.ic_topbar_icon),
          contentDescription = "empty",
          modifier = Modifier.size(width = 48.dp, height = 72.dp)
        )

        Text(
          text = "app icon",
          modifier = Modifier.padding(top = 4.dp),
          color = Color.Black,
          fontSize = 10.sp
        )
      }
    }
    Rune.entries
      .chunked(4)
      .forEach { rowRunes ->
        Row(
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          rowRunes.forEach { runeId ->
            Column(
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Image(
                painter = painterResource(runeId.drawable()),
                contentDescription = runeId.key,
                modifier = Modifier.size(width = 48.dp, height = 72.dp)
              )

              Text(
                text = runeId.key,
                modifier = Modifier.padding(top = 4.dp),
                color = Color.Black,
                fontSize = 10.sp
              )
            }
          }
        }
      }
  }
}