package com.taka.runejournal.feature.reading.ui

import com.conamobile.pdfkmp.PdfDocument
import com.conamobile.pdfkmp.composeresources.image
import com.conamobile.pdfkmp.composeresources.vector
import com.conamobile.pdfkmp.dsl.PageScope
import com.conamobile.pdfkmp.geometry.ContentScale
import com.conamobile.pdfkmp.geometry.Padding
import com.conamobile.pdfkmp.layout.BoxAlignment
import com.conamobile.pdfkmp.layout.HorizontalArrangement
import com.conamobile.pdfkmp.layout.PageBreakStrategy
import com.conamobile.pdfkmp.layout.VerticalAlignment
import com.conamobile.pdfkmp.pdfAsync
import com.conamobile.pdfkmp.style.BorderStroke
import com.conamobile.pdfkmp.style.PdfColor
import com.conamobile.pdfkmp.style.TextAlign
import com.conamobile.pdfkmp.unit.dp
import com.conamobile.pdfkmp.unit.sp
import com.conamobile.pdfkmp.viewer.KmpPdfLauncher
import com.taka.runejournal.core.domain.model.RuneOrientation
import com.taka.runejournal.core.ui.drawableVector
import com.taka.runejournal.core.ui.origin
import com.taka.runejournal.core.ui.toDotSeparatedKeywords
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.paper_texture
import taka_rune_journal.composeapp.generated.resources.reading_share_preview_topbar_title
import taka_rune_journal.composeapp.generated.resources.rune_display_name_reversed

/*
 * PDF visual system
 *
 * COLORS
 * Primary   #333333  Main text, titles, rune glyphs, QR
 * Secondary #6B6861  Supporting text, labels, metadata
 * Border    #8D887F  Certificate border
 *
 * FONT SIZES
 * Display       34sp  Recipient name
 * Title         22sp  Rune name
 * Section       16sp  Main reading title
 * Emphasis      14sp  Cover branding / question
 * Body          11sp  Interpretation / secondary prominent text
 * Small         10sp  Labels / metadata / supporting copy
 */

private object TakaPdfColors {
  val Primary = PdfColor.fromHex("#333333")
  val Secondary = PdfColor.fromHex("#6B6861")
  val Border = PdfColor.fromHex("#8D887F")
}

private object TakaPdfFontSizes {
  val Display = 34.sp
  val Title = 22.sp
  val Section = 16.sp
  val Emphasis = 14.sp
  val Body = 11.sp
  val Small = 10.sp

  val BodyLineHeight = 17.sp
}

private suspend fun generateReadingPdf(
  uiModel: ReadingPdfUiModel
): PdfDocument {
  val topic = getString(uiModel.topic.title())
  val spread = getString(uiModel.spread.title)

  val introPage = getIntroPage(
    createdAt = uiModel.createdAt,
    topic = topic,
    spread = spread,
    recipient = uiModel.recipient,
    reader = uiModel.reader,
    question = uiModel.question,
    runes = uiModel.runes,
  )

  val runePages = mutableListOf<PageScope.() -> Unit>()

  for (runeUiModel in uiModel.runes) {
    val position =
      runeUiModel.position?.let { getString(it) }

    val positionDescription =
      runeUiModel.positionDescription?.let { getString(it) }

    val keywords =
      runeUiModel.supplementalKeywords?.let {
        (
            getString(runeUiModel.keywords) +
                ", " +
                getString(runeUiModel.supplementalKeywords)
            ).toDotSeparatedKeywords()
      } ?: getString(runeUiModel.keywords).toDotSeparatedKeywords()

    val fullInterpretation = listOfNotNull(
      getString(runeUiModel.rune.origin()),
      getString(runeUiModel.interpretation),
      runeUiModel.supplementalInterpretation?.let { getString(it) },
    ).joinToString("\n\n")

    val drawnRuneName =
      if (runeUiModel.orientation == RuneOrientation.REVERSED) {
        getString(
          Res.string.rune_display_name_reversed,
          runeUiModel.rune.displayName,
        )
      } else {
        runeUiModel.rune.displayName
      }

    runePages += getRunePage(
      topic = topic,
      spread = spread,
      question = uiModel.question,
      position = position,
      positionDescription = positionDescription,
      drawnRuneName = drawnRuneName,
      runeUiModel = runeUiModel,
      keywords = keywords,
      fullInterpretation = fullInterpretation,
    )
  }

  val qrData = Json.encodeToString(uiModel.qrPayload)

  val importFooter = getImportFooter(
    qrData = qrData,
  )

  val background: PageScope.() -> Unit = {
    val pageWidth = size.width
    val pageHeight = size.height
    val pageInset = 32.dp

    watermark {
      image(
        resource = Res.drawable.paper_texture,
        width = pageWidth,
        height = pageHeight,
        contentScale = ContentScale.Crop,
      )

      aligned(BoxAlignment.Center) {
        box(
          width = pageWidth - (pageInset * 2),
          height = pageHeight - (pageInset * 2),
          border = BorderStroke(
            width = 1.dp,
            color = TakaPdfColors.Border,
          ),
          cornerRadius = 6.dp,
        ) {}
      }
    }
  }

  return pdfAsync {
    defaultPageBreakStrategy = PageBreakStrategy.Slice

    page {
      background()
      introPage()
    }

    for ((index, runePage) in runePages.withIndex()) {
      page {
        background()
        runePage()

        if (index == runePages.lastIndex) {
          importFooter()
        }
      }
    }
  }
}

suspend fun previewReadingPdf(
  uiModel: ReadingPdfUiModel
) {
  val document = generateReadingPdf(uiModel)

  KmpPdfLauncher.open(
    document = document,
    title = getString(
      Res.string.reading_share_preview_topbar_title
    ),
    fileName = "taka-reading-${uiModel.id}.pdf",
    showSearch = false,
  )
}

private fun getIntroPage(
  createdAt: String,
  topic: String,
  spread: String,
  recipient: String,
  reader: String,
  question: String?,
  runes: List<ReadingPdfDrawnRuneUiModel>,
): PageScope.() -> Unit = {
  val pageInset = 32.dp
  val contentWidth = size.width - (pageInset * 2)
  val contentHeight = size.height - (pageInset * 2)

  padding = Padding.all(pageInset)

  box(
    width = contentWidth,
    height = contentHeight,
  ) {
    // Branding
    aligned(BoxAlignment.TopCenter) {
      spacer(height = 28.dp)

      text("TAKA") {
        fontSize = TakaPdfFontSizes.Emphasis
        bold = true
        letterSpacing = 4.sp
        color = TakaPdfColors.Primary
        align = TextAlign.Center
      }

      spacer(height = 3.dp)

      text("Rune Journal") {
        fontSize = TakaPdfFontSizes.Small
        letterSpacing = 1.sp
        color = TakaPdfColors.Secondary
        align = TextAlign.Center
      }
    }

    // Main personalized content
    aligned(BoxAlignment.Center) {
      text("A READING FOR") {
        fontSize = TakaPdfFontSizes.Small
        bold = true
        letterSpacing = 2.sp
        color = TakaPdfColors.Secondary
        align = TextAlign.Center
      }

      spacer(height = 12.dp)

      text(recipient) {
        fontSize = TakaPdfFontSizes.Display
        bold = true
        color = TakaPdfColors.Primary
        align = TextAlign.Center
      }

      reader
        .takeIf { it.isNotBlank() }
        ?.let {
          spacer(height = 4.dp)

          text("from $it") {
            fontSize = TakaPdfFontSizes.Body
            color = TakaPdfColors.Secondary
            align = TextAlign.Center
          }
        }

      spacer(height = 32.dp)

      text("$topic Reading") {
        fontSize = TakaPdfFontSizes.Section
        bold = true
        color = TakaPdfColors.Primary
        align = TextAlign.Center
      }

      spacer(height = 6.dp)

      text(spread) {
        fontSize = TakaPdfFontSizes.Body
        color = TakaPdfColors.Secondary
        align = TextAlign.Center
      }

      question
        ?.takeIf { it.isNotBlank() }
        ?.let {
          spacer(height = 28.dp)

          text("“$it”") {
            fontSize = TakaPdfFontSizes.Emphasis
            italic = true
            color = TakaPdfColors.Primary
            align = TextAlign.Center
          }
        }

      spacer(height = 30.dp)

      row(
        spacing = 24.dp,
        horizontalArrangement = HorizontalArrangement.Center,
      ) {
        runes.forEach { runeUiModel ->
          val coverRuneWidth = 32.dp
          val coverRuneHeight = 48.dp

          box(
            width = coverRuneWidth,
            height = coverRuneHeight,
            rotation =
              if (
                runeUiModel.orientation ==
                RuneOrientation.REVERSED
              ) {
                180f
              } else {
                0f
              },
          ) {
            aligned(BoxAlignment.Center) {
              vector(
                resource = runeUiModel.rune.drawableVector(),
                width = coverRuneWidth,
                height = coverRuneHeight,
                tint = TakaPdfColors.Primary,
              )
            }
          }
        }
      }
    }

    // Date
    aligned(BoxAlignment.BottomCenter) {
      text(createdAt) {
        fontSize = TakaPdfFontSizes.Small
        color = TakaPdfColors.Secondary
        align = TextAlign.Center
      }

      spacer(height = 28.dp)
    }
  }
}

private fun getRunePage(
  topic: String,
  spread: String,
  question: String?,
  position: String?,
  positionDescription: String?,
  drawnRuneName: String,
  runeUiModel: ReadingPdfDrawnRuneUiModel,
  keywords: String,
  fullInterpretation: String,
): PageScope.() -> Unit = {
  val pageInset = 56.dp
  val contentWidth = size.width - (pageInset * 2)

  padding = Padding.symmetric(
    horizontal = pageInset,
    vertical = 48.dp,
  )

  // Branding
  text("TAKA") {
    fontSize = TakaPdfFontSizes.Small
    bold = true
    letterSpacing = 3.sp
    color = TakaPdfColors.Primary
    align = TextAlign.Center
  }

  spacer(height = 22.dp)

  // Reading context
  text("$topic Reading") {
    fontSize = TakaPdfFontSizes.Body
    bold = true
    color = TakaPdfColors.Primary
    align = TextAlign.Center
  }

  spacer(height = 4.dp)

  text(spread) {
    fontSize = TakaPdfFontSizes.Small
    color = TakaPdfColors.Secondary
    align = TextAlign.Center
  }

  question
    ?.takeIf { it.isNotBlank() }
    ?.let {
      spacer(height = 14.dp)

      text("“$it”") {
        fontSize = TakaPdfFontSizes.Small
        italic = true
        color = TakaPdfColors.Secondary
        align = TextAlign.Center
      }
    }

  spacer(height = 30.dp)

  // Position
  position?.let {
    text(it.uppercase()) {
      fontSize = TakaPdfFontSizes.Small
      bold = true
      letterSpacing = 2.sp
      color = TakaPdfColors.Secondary
      align = TextAlign.Center
    }
  }

  positionDescription?.let {
    spacer(height = 6.dp)

    text(it) {
      fontSize = TakaPdfFontSizes.Small
      color = TakaPdfColors.Secondary
      align = TextAlign.Center
    }
  }

  if (
    position != null ||
    positionDescription != null
  ) {
    spacer(height = 24.dp)
  }

  // Rune
  val runeWidth = 72.dp
  val runeHeight = 108.dp

  box(
    width = contentWidth,
    height = runeHeight,
  ) {
    aligned(BoxAlignment.Center) {
      box(
        width = runeWidth,
        height = runeHeight,
        rotation =
          if (
            runeUiModel.orientation ==
            RuneOrientation.REVERSED
          ) {
            180f
          } else {
            0f
          },
      ) {
        aligned(BoxAlignment.Center) {
          vector(
            resource = runeUiModel.rune.drawableVector(),
            width = runeWidth,
            height = runeHeight,
            tint = TakaPdfColors.Primary,
          )
        }
      }
    }
  }

  spacer(height = 14.dp)

  text(drawnRuneName) {
    fontSize = TakaPdfFontSizes.Title
    bold = true
    color = TakaPdfColors.Primary
    align = TextAlign.Center
  }

  spacer(height = 7.dp)

  text(keywords) {
    fontSize = TakaPdfFontSizes.Small
    color = TakaPdfColors.Secondary
    align = TextAlign.Center
  }

  spacer(height = 32.dp)

  // Interpretation
  text(fullInterpretation) {
    fontSize = TakaPdfFontSizes.Body
    lineHeight = TakaPdfFontSizes.BodyLineHeight
    color = TakaPdfColors.Primary
    align = TextAlign.Start
  }
}

private fun getImportFooter(
  qrData: String,
): PageScope.() -> Unit = {
  spacer(height = 32.dp)

  row(
    spacing = 14.dp,
    horizontalArrangement = HorizontalArrangement.Center,
    verticalAlignment = VerticalAlignment.Center,
  ) {
    qrCode(
      data = qrData,
      size = 64.dp,
      color = TakaPdfColors.Primary,
      background = PdfColor.White,
    )

    column(
      spacing = 4.dp,
    ) {
      text("Keep this reading in Taka") {
        fontSize = TakaPdfFontSizes.Small
        bold = true
        color = TakaPdfColors.Primary
      }

      text(
        "Scan to import this reading into Taka Rune Journal."
      ) {
        fontSize = TakaPdfFontSizes.Small
        color = TakaPdfColors.Secondary
      }
    }
  }
}