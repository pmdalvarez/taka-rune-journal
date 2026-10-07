package com.taka.runejournal.feature.reading.ui

import com.conamobile.pdfkmp.PdfDocument
import com.conamobile.pdfkmp.pdfAsync
import com.conamobile.pdfkmp.viewer.KmpPdfLauncher

suspend fun generateReadingPdf(uiModel: ReadingPdfUiModel): PdfDocument = pdfAsync {
  page {
    text("Your Reading")
  }
}

suspend fun previewReadingPdf(previewTitle: String, uiModel: ReadingPdfUiModel) {
  val document = generateReadingPdf(uiModel)
  KmpPdfLauncher.open(
    document = document,
    title = previewTitle,
    fileName = "taka-reading-${uiModel.id}.pdf",
    showSearch = false
  )
}