package com.taka.runejournal.feature.reading.ui

import com.conamobile.pdfkmp.PdfDocument
import com.conamobile.pdfkmp.composeresources.image
import com.conamobile.pdfkmp.composeresources.toVectorImage
import com.conamobile.pdfkmp.composeresources.vector
import com.conamobile.pdfkmp.dsl.ContainerScope
import com.conamobile.pdfkmp.dsl.PageScope
import com.conamobile.pdfkmp.geometry.ContentScale
import com.conamobile.pdfkmp.geometry.PageSize
import com.conamobile.pdfkmp.geometry.Padding
import com.conamobile.pdfkmp.layout.BoxAlignment
import com.conamobile.pdfkmp.layout.HorizontalArrangement
import com.conamobile.pdfkmp.layout.VerticalAlignment
import com.conamobile.pdfkmp.pdfAsync
import com.conamobile.pdfkmp.style.BorderStroke
import com.conamobile.pdfkmp.style.PdfColor
import com.conamobile.pdfkmp.style.TextAlign
import com.conamobile.pdfkmp.unit.Dp
import com.conamobile.pdfkmp.unit.dp
import com.conamobile.pdfkmp.unit.sp
import com.conamobile.pdfkmp.vector.VectorImage
import com.conamobile.pdfkmp.viewer.KmpPdfLauncher
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.RuneOrientation
import com.taka.runejournal.core.domain.model.RuneSpread
import com.taka.runejournal.core.ui.drawableVector
import com.taka.runejournal.core.ui.origin
import com.taka.runejournal.core.ui.toDotSeparatedKeywords
import com.taka.runejournal.feature.reading.data.encode
import org.jetbrains.compose.resources.getString
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.app_name
import taka_rune_journal.composeapp.generated.resources.ic_topbar_icon
import taka_rune_journal.composeapp.generated.resources.paper_texture
import taka_rune_journal.composeapp.generated.resources.reading_share_preview_topbar_title
import taka_rune_journal.composeapp.generated.resources.rune_display_name_reversed
import kotlin.math.ceil

/*
 * PDF visual system
 *
 * The reading is one continuous page at phone width, so it scrolls like the app on a phone.
 *
 * COLORS
 * Brand     #4F5D64  Taka branding; matches the app light-theme primary
 * Primary   #333333  Main text, titles, rune glyphs, QR
 * Secondary #6B6861  Supporting text, labels, metadata
 * Border    #8D887F  Page border and section dividers
 *
 * FONT SIZES
 * Display       24sp  Reading title, e.g. "A Reading for Ray"
 * Title         18sp  Rune name
 * Emphasis      14sp  Branding / topic / question
 * Body        12.5sp  Interpretation
 * Small         10sp  Labels / metadata / supporting copy
 */

private val pdfBrandColor = PdfColor.fromHex("#4F5D64")
private val pdfPrimaryColor = PdfColor.fromHex("#333333")
private val pdfSecondaryColor = PdfColor.fromHex("#6B6861")
private val pdfBorderColor = PdfColor.fromHex("#8D887F")

private val pdfDisplayFontSize = 24.sp
private val pdfTitleFontSize = 18.sp
private val pdfEmphasisFontSize = 14.sp
private val pdfBodyFontSize = 12.5.sp
private val pdfSmallFontSize = 10.sp

private val pdfBodyLineHeight = 19.sp

private val pdfBrandWidth = 90.dp
private val pdfBrandHeight = 36.dp
private val pdfBrandIconSize = 36.dp
private val pdfBrandTextStart = 30.dp
private val pdfBrandLetterSpacing = 1.5.sp

private val pdfPageWidth = 400.dp
private val pdfBorderInset = 14.dp
private val pdfContentPadding = Padding.symmetric(horizontal = 38.dp, vertical = 40.dp)

// Tall enough for any reading; only used to measure where the content ends
private val pdfMeasuringPageHeight = 10000.dp
// Text runs give glyph boxes, not layout line boxes; the buffer stops a fraction of a line spilling onto a second page
private val pdfMeasuringBuffer = 12.dp

private val pdfOverviewRuneWidth = 26.dp
private val pdfOverviewRuneHeight = 39.dp
private val pdfSectionRuneWidth = 40.dp
private val pdfSectionRuneHeight = 60.dp
private val pdfQrCodeSize = 88.dp

private const val IMPORT_FOOTER_TITLE = "Keep this reading in Taka"
private const val IMPORT_FOOTER_DESCRIPTION = "Scan to import this reading into Taka Rune Journal."

private class PdfRuneSection(
  val position: String?,
  val positionDescription: String?,
  val drawnRuneName: String,
  val keywords: String,
  val fullInterpretation: String,
  val runeUiModel: ReadingPdfDrawnRuneUiModel,
)

/**
 * Builds the reading as a single phone-width page whose height fits the content.
 *
 * PdfKmp needs the page height up front, so the document is generated twice: first on a very tall
 * page to find where the content ends, then again at exactly that height.
 */
private suspend fun generateReadingPdf(
  uiModel: ReadingPdfUiModel,
): PdfDocument {
  val appName = getString(Res.string.app_name).uppercase()

  // Resolve the brand icon before entering the PDF DSL, where lazy Compose resource nodes can fail
  val brandIcon = Res.drawable.ic_topbar_icon.toVectorImage()

  val topic = if (uiModel.topic != ReadingTopic.GENERAL) getString(uiModel.topic.title()) else ""
  // A single rune needs no spread name or rune overview; both are shown for multi-rune spreads only
  val isMultiRuneSpread = uiModel.spread != RuneSpread.SINGLE_RUNE
  val spread = if (isMultiRuneSpread) getString(uiModel.spread.title) else ""

  val sections = uiModel.runes.map { runeUiModel ->
    val keywords =
      runeUiModel.supplementalKeywords?.let {
        (getString(runeUiModel.keywords) + ", " + getString(it)).toDotSeparatedKeywords()
      } ?: getString(runeUiModel.keywords).toDotSeparatedKeywords()

    PdfRuneSection(
      position = runeUiModel.position?.let { getString(it) },
      positionDescription = runeUiModel.positionDescription?.let { getString(it) },
      drawnRuneName =
        if (runeUiModel.orientation == RuneOrientation.REVERSED) {
          getString(Res.string.rune_display_name_reversed, runeUiModel.rune.displayName)
        } else {
          runeUiModel.rune.displayName
        },
      keywords = keywords,
      fullInterpretation = listOfNotNull(
        getString(runeUiModel.rune.origin()),
        getString(runeUiModel.interpretation),
        runeUiModel.supplementalInterpretation?.let { getString(it) },
      ).joinToString("\n\n"),
      runeUiModel = runeUiModel,
    )
  }

  val content: PageScope.() -> Unit = {
    padding = pdfContentPadding
    val contentWidth = size.width - pdfContentPadding.left - pdfContentPadding.right

    titleBlock(
      appName = appName,
      brandIcon = brandIcon,
      recipient = uiModel.recipient,
      reader = uiModel.reader,
      createdAt = uiModel.createdAt,
      topic = topic,
      spread = spread,
      question = uiModel.question,
      overviewRunes = if (isMultiRuneSpread) uiModel.runes else emptyList(),
    )

    sections.forEach { section ->
      runeSection(section = section, contentWidth = contentWidth)
    }

    importFooter(qrData = uiModel.qrPayload.encode())
  }

  val measuringDocument = renderReadingPdf(
    pageHeight = pdfMeasuringPageHeight,
    drawBackground = false,
    content = content,
  )

  return renderReadingPdf(
    pageHeight = measureContentHeight(measuringDocument),
    drawBackground = true,
    content = content,
  )
}

private suspend fun renderReadingPdf(
  pageHeight: Dp,
  drawBackground: Boolean,
  content: PageScope.() -> Unit,
): PdfDocument = pdfAsync {
  page(size = PageSize.custom(pdfPageWidth, pageHeight)) {
    if (drawBackground) {
      background()
    }
    content()
  }
}

/**
 * Returns the page height that fits the content of a document rendered on the measuring page.
 *
 * The last text on the page is the import footer. Its QR code is vertically centred against the
 * footer text and taller than it, so the bottom of the QR code is calculated from that text.
 */
private fun measureContentHeight(document: PdfDocument): Dp {
  val runs = document.textRuns.filter { it.pageIndex == 0 }
  val textBottom = runs.maxOfOrNull { it.yPoints + it.heightPoints } ?: return pdfMeasuringPageHeight
  val footerTop = runs.lastOrNull { it.text == IMPORT_FOOTER_TITLE }?.yPoints ?: textBottom
  val qrBottom = (footerTop + textBottom) / 2f + pdfQrCodeSize.value / 2f
  return (maxOf(textBottom, qrBottom) + pdfContentPadding.bottom.value + pdfMeasuringBuffer.value).dp
}

/**
 * Paper texture tiled down the page, so it keeps its scale on a tall page, plus an inset border.
 */
private fun PageScope.background() {
  val pageWidth = size.width
  val pageHeight = size.height
  // The texture is square, so each tile is as tall as the page is wide
  val tileCount = ceil(pageHeight.value / pageWidth.value).toInt()

  watermark {
    column {
      repeat(tileCount) { index ->
        val remainingHeight = pageHeight - pageWidth * index
        image(
          resource = Res.drawable.paper_texture,
          width = pageWidth,
          height = if (remainingHeight < pageWidth) remainingHeight else pageWidth,
          contentScale = ContentScale.Crop,
        )
      }
    }

    aligned(BoxAlignment.Center) {
      box(
        width = pageWidth - pdfBorderInset * 2,
        height = pageHeight - pdfBorderInset * 2,
        border = BorderStroke(
          width = 1.dp,
          color = pdfBorderColor,
        ),
        cornerRadius = 6.dp,
      ) {}
    }
  }
}

private fun PageScope.titleBlock(
  appName: String,
  brandIcon: VectorImage,
  recipient: String,
  reader: String,
  createdAt: String,
  topic: String,
  spread: String,
  question: String?,
  overviewRunes: List<ReadingPdfDrawnRuneUiModel>,
) {
  row(horizontalArrangement = HorizontalArrangement.Center) {
    takaBrandLockup(
      appName = appName,
      brandIcon = brandIcon,
    )
  }

  spacer(height = 24.dp)

  text("A Reading for $recipient") {
    fontSize = pdfDisplayFontSize
    bold = true
    color = pdfPrimaryColor
    align = TextAlign.Center
  }

  spacer(height = 6.dp)

  val meta = if (reader.isNotBlank()) "from $reader · $createdAt" else createdAt
  text(meta) {
    fontSize = pdfSmallFontSize
    color = pdfSecondaryColor
    align = TextAlign.Center
  }

  if (topic.isNotBlank()) {
    spacer(height = 18.dp)

    text(topic) {
      fontSize = pdfEmphasisFontSize
      color = pdfPrimaryColor
      align = TextAlign.Center
    }
  }

  if (spread.isNotBlank()) {
    if (topic.isNotBlank()) {
      // Below the topic the spread is supporting detail
      spacer(height = 4.dp)

      text(spread) {
        fontSize = pdfSmallFontSize
        color = pdfSecondaryColor
        align = TextAlign.Center
      }
    } else {
      // Without a topic the spread takes the topic's place
      spacer(height = 18.dp)

      text(spread) {
        fontSize = pdfEmphasisFontSize
        color = pdfPrimaryColor
        align = TextAlign.Center
      }
    }
  }

  question
    ?.takeIf { it.isNotBlank() }
    ?.let {
      spacer(height = 14.dp)

      text("“$it”") {
        fontSize = pdfEmphasisFontSize
        bold = true
        color = pdfPrimaryColor
        align = TextAlign.Center
      }
    }

  if (overviewRunes.isNotEmpty()) {
    spacer(height = 18.dp)

    row(
      spacing = 22.dp,
      horizontalArrangement = HorizontalArrangement.Center,
    ) {
      overviewRunes.forEach { runeUiModel ->
        runeGlyph(
          runeUiModel = runeUiModel,
          width = pdfOverviewRuneWidth,
          height = pdfOverviewRuneHeight,
        )
      }
    }
  }
}

private fun PageScope.runeSection(
  section: PdfRuneSection,
  contentWidth: Dp,
) {
  sectionDivider()

  section.position?.let {
    text(it.uppercase()) {
      fontSize = pdfSmallFontSize
      bold = true
      letterSpacing = 2.sp
      color = pdfSecondaryColor
      align = TextAlign.Center
    }
  }

  section.positionDescription?.let {
    spacer(height = 4.dp)

    text(it) {
      fontSize = pdfSmallFontSize
      italic = true
      color = pdfSecondaryColor
      align = TextAlign.Center
    }
  }

  if (section.position != null || section.positionDescription != null) {
    spacer(height = 16.dp)
  }

  box(
    width = contentWidth,
    height = pdfSectionRuneHeight,
  ) {
    aligned(BoxAlignment.Center) {
      runeGlyph(
        runeUiModel = section.runeUiModel,
        width = pdfSectionRuneWidth,
        height = pdfSectionRuneHeight,
      )
    }
  }

  spacer(height = 10.dp)

  text(section.drawnRuneName) {
    fontSize = pdfTitleFontSize
    bold = true
    color = pdfPrimaryColor
    align = TextAlign.Center
  }

  spacer(height = 4.dp)

  text(section.keywords) {
    fontSize = pdfSmallFontSize
    color = pdfSecondaryColor
    align = TextAlign.Center
  }

  spacer(height = 18.dp)

  text(section.fullInterpretation) {
    fontSize = pdfBodyFontSize
    lineHeight = pdfBodyLineHeight
    color = pdfPrimaryColor
    align = TextAlign.Start
  }
}

private fun PageScope.importFooter(
  qrData: String,
) {
  sectionDivider(top = 30.dp)

  row(
    spacing = 14.dp,
    horizontalArrangement = HorizontalArrangement.Center,
    verticalAlignment = VerticalAlignment.Center,
  ) {
    qrCode(
      data = qrData,
      size = pdfQrCodeSize,
      color = pdfPrimaryColor,
      background = PdfColor.White,
    )

    column(
      spacing = 3.dp,
    ) {
      text(IMPORT_FOOTER_TITLE) {
        fontSize = pdfSmallFontSize
        bold = true
        color = pdfPrimaryColor
      }

      text(IMPORT_FOOTER_DESCRIPTION) {
        fontSize = pdfSmallFontSize
        color = pdfSecondaryColor
      }
    }
  }
}

private fun PageScope.sectionDivider(
  top: Dp = 24.dp,
) {
  spacer(height = top)
  divider(
    thickness = 0.5.dp,
    color = pdfBorderColor,
  )
  spacer(height = 24.dp)
}

private fun ContainerScope.runeGlyph(
  runeUiModel: ReadingPdfDrawnRuneUiModel,
  width: Dp,
  height: Dp,
) {
  box(
    width = width,
    height = height,
    rotation = if (runeUiModel.orientation == RuneOrientation.REVERSED) 180f else 0f,
  ) {
    aligned(BoxAlignment.Center) {
      vector(
        resource = runeUiModel.rune.drawableVector(),
        width = width,
        height = height,
        tint = pdfPrimaryColor,
      )
    }
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
        tint = pdfBrandColor,
      )
    }

    aligned(BoxAlignment.CenterStart) {
      row {
        spacer(width = pdfBrandTextStart)

        text(appName) {
          fontSize = pdfEmphasisFontSize
          bold = true
          letterSpacing = pdfBrandLetterSpacing
          color = pdfBrandColor
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
