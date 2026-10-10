package com.taka.runejournal.feature.reading.ui

import com.conamobile.pdfkmp.PdfDocument
import com.conamobile.pdfkmp.composeresources.image
import com.conamobile.pdfkmp.dsl.PageScope
import com.conamobile.pdfkmp.geometry.ContentScale
import com.conamobile.pdfkmp.geometry.Padding
import com.conamobile.pdfkmp.geometry.PageSize
import com.conamobile.pdfkmp.layout.BoxAlignment
import com.conamobile.pdfkmp.layout.HorizontalArrangement
import com.conamobile.pdfkmp.layout.PageBreakStrategy
import com.conamobile.pdfkmp.pdfAsync
import com.conamobile.pdfkmp.style.BorderStroke
import com.conamobile.pdfkmp.style.PdfColor
import com.conamobile.pdfkmp.style.TextAlign
import com.conamobile.pdfkmp.unit.dp
import com.conamobile.pdfkmp.unit.sp
import com.conamobile.pdfkmp.viewer.KmpPdfLauncher
import com.taka.runejournal.core.domain.model.RuneOrientation
import com.taka.runejournal.core.ui.drawable
import com.taka.runejournal.core.ui.origin
import com.taka.runejournal.core.ui.toDotSeparatedKeywords
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.paper_texture
import taka_rune_journal.composeapp.generated.resources.reading_share_preview_topbar_title
import taka_rune_journal.composeapp.generated.resources.rune_display_name_reversed

private suspend fun generateReadingPdf(uiModel: ReadingPdfUiModel): PdfDocument {
  val introPage = getIntroPage(
    createdAt = uiModel.createdAt,
    topic = getString(uiModel.topic.title()),
    spread = getString(uiModel.spread.title),
    recipient = uiModel.recipient,
    reader = uiModel.reader,
    question = uiModel.question,
    runes = uiModel.runes
  )
  val footer: PageScope.() -> Unit = {
    qrCode(
      data = Json.encodeToString(uiModel.qrPayload),
      size = 120.dp,
    )
  }

  val runePages: MutableList<PageScope.() -> Unit> = mutableListOf()
  for (runeUiModel in uiModel.runes) {
    val position = runeUiModel.position?.let { getString(it) }
    val positionDescription = runeUiModel.positionDescription?.let { getString(it) }
    val keywords = runeUiModel.supplementalKeywords?.let {
      (getString(runeUiModel.keywords) + ", " + getString(runeUiModel.supplementalKeywords)).toDotSeparatedKeywords()
    } ?: getString(runeUiModel.keywords).toDotSeparatedKeywords()
    val fullInterpretation = listOfNotNull(
      getString(runeUiModel.rune.origin()),
      getString(runeUiModel.interpretation),
      runeUiModel.supplementalInterpretation?.let { getString(it) }
    ).joinToString("\n\n")
    val drawnRuneName = if (runeUiModel.orientation == RuneOrientation.REVERSED) {
      getString(Res.string.rune_display_name_reversed, runeUiModel.rune.displayName)
    } else {
      runeUiModel.rune.displayName
    }

    val runeWidth = 96.dp
    val runeHeight = 144.dp
    runePages.add(
      {
        position?.let { text(it) }
        positionDescription?.let { text(it) }
        text(drawnRuneName)
        box(
          width = runeWidth,
          height = runeHeight,
          rotation = if (runeUiModel.orientation == RuneOrientation.REVERSED) 180f else 0f,
        ) {
          aligned(BoxAlignment.Center) {
            image(
              resource = runeUiModel.rune.drawable(),
              width = runeWidth,
              height = runeHeight,
              contentScale = ContentScale.Fit
            )
          }
        }
        text(keywords)
        text(fullInterpretation)
      }
    )
  }

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

      // border
      aligned(BoxAlignment.Center) {
        box(
          width = pageWidth - (pageInset * 2),
          height = pageHeight - (pageInset * 2),
          border = BorderStroke(
            width = 1.dp,
            color = PdfColor.fromHex("#8D887F"),
          ),
          cornerRadius = 6.dp,
        ) {}
      }
    }
  }
  return pdfAsync {
    defaultPageBreakStrategy  = PageBreakStrategy.Slice
    page {
      background()
      introPage()
    }
    for (runePage in runePages) {
      page{
        background()
        runePage()
        footer()
      }
    }
  }
}

suspend fun previewReadingPdf(uiModel: ReadingPdfUiModel) {
  val document = generateReadingPdf(uiModel)
  KmpPdfLauncher.open(
    document = document,
    title = getString(Res.string.reading_share_preview_topbar_title),
    fileName = "taka-reading-${uiModel.id}.pdf",
    showSearch = false
  )
}

private fun getIntroPage(
  createdAt: String,
  topic: String,
  spread: String,
  recipient: String,
  reader: String,
  question: String?,
  runes: List<ReadingPdfDrawnRuneUiModel>
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
        fontSize = 14.sp
        bold = true
        letterSpacing = 4.sp
        color = PdfColor.fromHex("#333333")
        align = TextAlign.Center
      }

      spacer(height = 3.dp)

      text("Rune Journal") {
        fontSize = 9.sp
        letterSpacing = 1.sp
        color = PdfColor.fromHex("#6B6861")
        align = TextAlign.Center
      }
    }

    // Main personalized content
    aligned(BoxAlignment.Center) {
      text("A READING FOR") {
        fontSize = 10.sp
        bold = true
        letterSpacing = 2.sp
        color = PdfColor.fromHex("#6B6861")
        align = TextAlign.Center
      }

      spacer(height = 12.dp)

      text(recipient) {
        fontSize = 34.sp
        bold = true
        color = PdfColor.fromHex("#292929")
        align = TextAlign.Center
      }

      reader.takeIf { it.isNotBlank() }
        ?.let { reader ->
          spacer(height = 4.dp)

          text("from $reader") {
            fontSize = 11.sp
            color = PdfColor.fromHex("#6B6861")
            align = TextAlign.Center
          }
        }

      spacer(height = 32.dp)

      text("$topic Reading") {
        fontSize = 16.sp
        bold = true
        color = PdfColor.fromHex("#333333")
        align = TextAlign.Center
      }

      spacer(height = 6.dp)

      text(spread) {
        fontSize = 11.sp
        color = PdfColor.fromHex("#6B6861")
        align = TextAlign.Center
      }

      question
        ?.takeIf { it.isNotBlank() }
        ?.let { question ->
          spacer(height = 28.dp)

          text("“$question”") {
            fontSize = 14.sp
            italic = true
            color = PdfColor.fromHex("#3D3D3D")
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
            rotation = if (runeUiModel.orientation == RuneOrientation.REVERSED) {
              180f
            } else {
              0f
            },
          ) {
            aligned(BoxAlignment.Center) {
              image(
                resource = runeUiModel.rune.drawable(),
                width = coverRuneWidth,
                height = coverRuneHeight,
                contentScale = ContentScale.Fit,
              )
            }
          }
        }
      }
    }

    // Date
    aligned(BoxAlignment.BottomCenter) {
      text(createdAt) {
        fontSize = 10.sp
        color = PdfColor.fromHex("#6B6861")
        align = TextAlign.Center
      }

      spacer(height = 28.dp)
    }
  }
}