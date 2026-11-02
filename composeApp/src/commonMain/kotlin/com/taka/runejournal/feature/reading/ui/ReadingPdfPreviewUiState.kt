package com.taka.runejournal.feature.reading.ui

import com.conamobile.pdfkmp.PdfDocument

data class ReadingPdfPreviewUiState(
  val document: PdfDocument? = null,
  val fileName: String = "",
)
