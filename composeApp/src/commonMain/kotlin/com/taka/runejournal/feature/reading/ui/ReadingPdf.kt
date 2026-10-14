package com.taka.runejournal.feature.reading.ui

import com.conamobile.pdfkmp.PdfDocument
import com.conamobile.pdfkmp.composeresources.image
import com.conamobile.pdfkmp.composeresources.toVectorImage
import com.conamobile.pdfkmp.composeresources.vector
import com.conamobile.pdfkmp.dsl.ContainerScope
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
import com.conamobile.pdfkmp.vector.VectorImage
import com.conamobile.pdfkmp.viewer.KmpPdfLauncher
import com.taka.runejournal.core.domain.model.RuneOrientation
import com.taka.runejournal.core.ui.drawableVector
import com.taka.runejournal.core.ui.origin
import com.taka.runejournal.core.ui.toDotSeparatedKeywords
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.app_name
import taka_rune_journal.composeapp.generated.resources.ic_topbar_icon
import taka_rune_journal.composeapp.generated.resources.paper_texture
import taka_rune_journal.composeapp.generated.resources.reading_share_preview_topbar_title
import taka_rune_journal.composeapp.generated.resources.rune_display_name_reversed

/*
 * PDF visual system
 *
 * COLORS
 * Primary   #333333  Main text, titles, rune glyphs, branding, QR
 * Secondary #6B6861  Supporting text, labels, metadata
 * Border    #8D887F  Certificate border and header divider
 *
 * FONT SIZES
 * Display       34sp  Recipient name
 * Title         22sp  Rune name
 * Section       16sp  Main reading title
 * Emphasis      14sp  Branding / cover question
 * Body          11sp  Interpretation / prominent secondary text
 * Small         10sp  Labels / metadata / supporting copy
 */

private val pdfPrimaryColor = PdfColor.fromHex("#333333")
private val pdfSecondaryColor = PdfColor.fromHex("#6B6861")
private val pdfBorderColor = PdfColor.fromHex("#8D887F")

private val pdfDisplayFontSize = 34.sp
private val pdfTitleFontSize = 22.sp
private val pdfSectionFontSize = 16.sp
private val pdfEmphasisFontSize = 14.sp
private val pdfBodyFontSize = 11.sp
private val pdfSmallFontSize = 10.sp

private val pdfBodyLineHeight = 17.sp

// Shared Taka brand lockup
private val pdfBrandWidth = 90.dp
private val pdfBrandHeight = 30.dp
private val pdfBrandIconSize = 30.dp
private val pdfBrandTextStart = 24.dp
private val pdfBrandLetterSpacing = 1.5.sp

private suspend fun generateReadingPdf(
  uiModel: ReadingPdfUiModel,
): PdfDocument {
  val appName = getString(Res.string.app_name).uppercase()

  // Resolve this before entering the PDF DSL because running headers
  // cannot contain lazy Compose resource nodes.
  val brandIcon = Res.drawable.ic_topbar_icon.toVectorImage()

  val topic = getString(uiModel.topic.title())
  val spread = getString(uiModel.spread.title)

  val introPage = getIntroPage(
    appName = appName,
    brandIcon = brandIcon,
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
      appName = appName,
      brandIcon = brandIcon,
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
            color = pdfBorderColor,
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
  uiModel: ReadingPdfUiModel,
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
  appName: String,
  brandIcon: VectorImage,
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

      takaBrandLockup(
        appName = appName,
        brandIcon = brandIcon,
      )

      spacer(height = 3.dp)

      text("Rune Journal") {
        fontSize = pdfSmallFontSize
        letterSpacing = 1.sp
        color = pdfSecondaryColor
        align = TextAlign.Center
      }
    }

    // Main personalized content
    aligned(BoxAlignment.Center) {
      text("A READING FOR") {
        fontSize = pdfSmallFontSize
        bold = true
        letterSpacing = 2.sp
        color = pdfSecondaryColor
        align = TextAlign.Center
      }

      spacer(height = 12.dp)

      text(recipient) {
        fontSize = pdfDisplayFontSize
        bold = true
        color = pdfPrimaryColor
        align = TextAlign.Center
      }

      reader
        .takeIf { it.isNotBlank() }
        ?.let {
          spacer(height = 4.dp)

          text("from $it") {
            fontSize = pdfBodyFontSize
            color = pdfSecondaryColor
            align = TextAlign.Center
          }
        }

      spacer(height = 32.dp)

      text("$topic Reading") {
        fontSize = pdfSectionFontSize
        bold = true
        color = pdfPrimaryColor
        align = TextAlign.Center
      }

      spacer(height = 6.dp)

      text(spread) {
        fontSize = pdfBodyFontSize
        color = pdfSecondaryColor
        align = TextAlign.Center
      }

      question
        ?.takeIf { it.isNotBlank() }
        ?.let {
          spacer(height = 28.dp)

          text("“$it”") {
            fontSize = pdfEmphasisFontSize
            italic = true
            color = pdfPrimaryColor
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
                tint = pdfPrimaryColor,
              )
            }
          }
        }
      }
    }

    // Date
    aligned(BoxAlignment.BottomCenter) {
      text(createdAt) {
        fontSize = pdfSmallFontSize
        color = pdfSecondaryColor
        align = TextAlign.Center
      }

      spacer(height = 28.dp)
    }
  }
}

private fun getRunePage(
  appName: String,
  brandIcon: VectorImage,
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

  // Repeated reading context
  header { _ ->
    row(
      horizontalArrangement = HorizontalArrangement.SpaceBetween,
      verticalAlignment = VerticalAlignment.Center,
    ) {
      takaBrandLockup(
        appName = appName,
        brandIcon = brandIcon,
      )

      column(
        spacing = 3.dp,
      ) {
        text("$topic Reading") {
          fontSize = pdfSmallFontSize
          bold = true
          color = pdfPrimaryColor
          align = TextAlign.End
        }

        text(spread) {
          fontSize = pdfSmallFontSize
          color = pdfSecondaryColor
          align = TextAlign.End
        }
      }
    }

    question
      ?.takeIf { it.isNotBlank() }
      ?.let {
        spacer(height = 12.dp)

        text("“$it”") {
          fontSize = pdfSmallFontSize
          italic = true
          color = pdfSecondaryColor
          align = TextAlign.Center
        }
      }

    spacer(height = 12.dp)

    box(
      width = contentWidth,
      height = 1.dp,
      background = pdfBorderColor,
    ) {}
  }

  // Space between header and rune-specific content
  spacer(height = 28.dp)

  // Position
  position?.let {
    text(it.uppercase()) {
      fontSize = pdfSmallFontSize
      bold = true
      letterSpacing = 2.sp
      color = pdfSecondaryColor
      align = TextAlign.Center
    }
  }

  positionDescription?.let {
    spacer(height = 6.dp)

    text(it) {
      fontSize = pdfSmallFontSize
      color = pdfSecondaryColor
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
            tint = pdfPrimaryColor,
          )
        }
      }
    }
  }

  spacer(height = 14.dp)

  text(drawnRuneName) {
    fontSize = pdfTitleFontSize
    bold = true
    color = pdfPrimaryColor
    align = TextAlign.Center
  }

  spacer(height = 7.dp)

  text(keywords) {
    fontSize = pdfSmallFontSize
    color = pdfSecondaryColor
    align = TextAlign.Center
  }

  spacer(height = 32.dp)

  // Interpretation
  text(fullInterpretation) {
    fontSize = pdfBodyFontSize
    lineHeight = pdfBodyLineHeight
    color = pdfPrimaryColor
    align = TextAlign.Start
  }
}

private fun ContainerScope.takaBrandLockup(
  appName: String,
  brandIcon: VectorImage,
) {
  box(
    width = pdfBrandWidth,
    height = pdfBrandHeight,
  ) {
    aligned(BoxAlignment.CenterStart) {
      vector(
        image = brandIcon,
        width = pdfBrandIconSize,
        height = pdfBrandIconSize,
        tint = pdfPrimaryColor,
      )
    }

    aligned(BoxAlignment.CenterStart) {
      row {
        spacer(width = pdfBrandTextStart)

        text(appName) {
          fontSize = pdfEmphasisFontSize
          bold = true
          letterSpacing = pdfBrandLetterSpacing
          color = pdfPrimaryColor
        }
      }
    }
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
      color = pdfPrimaryColor,
      background = PdfColor.White,
    )

    column(
      spacing = 4.dp,
    ) {
      text("Keep this reading in Taka") {
        fontSize = pdfSmallFontSize
        bold = true
        color = pdfPrimaryColor
      }

      text(
        "Scan to import this reading into Taka Rune Journal."
      ) {
        fontSize = pdfSmallFontSize
        color = pdfSecondaryColor
      }
    }
  }
}