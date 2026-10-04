package com.taka.runejournal.feature.reading.ui

import com.conamobile.pdfkmp.PdfDocument
import com.conamobile.pdfkmp.pdfAsync
import com.conamobile.pdfkmp.storage.StorageLocation
import com.conamobile.pdfkmp.storage.save
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun generateReadingPdf(uiModel: ReadingPdfUiModel): PdfDocument = pdfAsync {
  page {
    text("Your Reading")
  }
}

suspend fun downloadReadingPdf(uiModel: ReadingPdfUiModel): Unit {
  val document = generateReadingPdf(uiModel)
  withContext(Dispatchers.Default) {
    document.save(
      location = StorageLocation.Downloads,
      filename = "taka-reading-${uiModel.id}.pdf", // TODO: change name here
    )
  }
}