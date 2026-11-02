package com.taka.runejournal.feature.reading.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taka.runejournal.core.domain.repository.SettingsRepository
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReadingPdfPreviewViewModel(
  private val id: Long,
  private val settingsRepository: SettingsRepository,
  private val timelineRepository: TimelineRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow(ReadingPdfPreviewUiState())
  val uiState: StateFlow<ReadingPdfPreviewUiState> = _uiState.asStateFlow()

  init {
    generatePdf()
  }

  private fun generatePdf() {
    viewModelScope.launch {
      val reader = settingsRepository.displayName.first()
      val pdfUiModel = when (val timelineItem = timelineRepository.getTimelineItem(id)) {
        is TimelineItem.SingleRuneReading -> timelineItem.toReadingPdfUiModel(reader)
        is TimelineItem.PpfRuneReading -> timelineItem.toReadingPdfUiModel(reader)
        // Only readings can be previewed; the screen keeps showing its loading indicator
        else -> return@launch
      }

      _uiState.value = ReadingPdfPreviewUiState(
        document = generateReadingPdf(pdfUiModel),
        fileName = "taka-reading-$id.pdf",
      )
    }
  }
}
