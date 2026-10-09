package com.taka.runejournal.feature.reading.ui

import com.conamobile.pdfkmp.PdfDocument
import com.conamobile.pdfkmp.composeresources.image
import com.conamobile.pdfkmp.dsl.PageScope
import com.conamobile.pdfkmp.geometry.ContentScale
import com.conamobile.pdfkmp.geometry.PageSize
import com.conamobile.pdfkmp.layout.BoxAlignment
import com.conamobile.pdfkmp.layout.PageBreakStrategy
import com.conamobile.pdfkmp.pdfAsync
import com.conamobile.pdfkmp.unit.dp
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
  val spread = getString(uiModel.spread.title)
  val topic = getString(uiModel.topic.title())
  val introPage: PageScope.() -> Unit = {
    text("date: " + uiModel.createdAt)
    text("Reader: " + uiModel.reader)
    text("Recipient: " + uiModel.recipient)
    text("Spread:" + spread)
    text("Topic:" + topic)
    text("Question:" + uiModel.question)
  }

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
    watermark {
      image(
        resource = Res.drawable.paper_texture,
        width = PageSize.A4.width,
        height =  PageSize.A4.height,
        contentScale = ContentScale.Crop,
      )
    }
  }

  return pdfAsync {
    defaultPageBreakStrategy  = PageBreakStrategy.Slice
    page {
      background()
      introPage()
      footer()
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