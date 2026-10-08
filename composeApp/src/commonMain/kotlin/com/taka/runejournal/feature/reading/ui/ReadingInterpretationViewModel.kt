package com.taka.runejournal.feature.reading.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.RuneSpread
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.core.ui.generalInterpretation
import com.taka.runejournal.core.ui.generalKeywords
import com.taka.runejournal.core.ui.supplementalInterpretation
import com.taka.runejournal.core.ui.supplementalKeywords
import com.taka.runejournal.core.ui.utils.format
import com.taka.runejournal.core.ui.utils.formatAbsolute
import com.taka.runejournal.feature.reading.domain.model.ReadingQrPayload
import com.taka.runejournal.feature.reading.domain.model.toReadingQrPayload
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_position_future_description
import taka_rune_journal.composeapp.generated.resources.reading_position_past_description
import taka_rune_journal.composeapp.generated.resources.reading_position_present_description
import taka_rune_journal.composeapp.generated.resources.reading_load_error
import taka_rune_journal.composeapp.generated.resources.reading_notes_save_error
import taka_rune_journal.composeapp.generated.resources.reading_tab_future_rune
import taka_rune_journal.composeapp.generated.resources.reading_tab_notes
import taka_rune_journal.composeapp.generated.resources.reading_tab_past_rune
import taka_rune_journal.composeapp.generated.resources.reading_tab_present_rune
import taka_rune_journal.composeapp.generated.resources.reading_tab_share
import taka_rune_journal.composeapp.generated.resources.reading_tab_single_rune
import taka_rune_journal.composeapp.generated.resources.timeline_delete_dialog_error
import kotlin.Long

class ReadingInterpretationViewModel(
  private val id: Long,
  private val timelineRepository: TimelineRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow(ReadingInterpretationUiState())
  val uiState: StateFlow<ReadingInterpretationUiState> = _uiState.asStateFlow()

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  init {
    loadReading()
  }

  private fun loadReading() {
    viewModelScope.launch {
      val timelineItem = timelineRepository.getTimelineItem(id)
      when (timelineItem) {
        is TimelineItem.SingleRuneReading -> {
          _uiState.value = ReadingInterpretationUiState(
            id = timelineItem.id,
            createdAt = timelineItem.createdAt.format(),
            topic = timelineItem.topic,
            recipient = timelineItem.recipient,
            question = timelineItem.question,
            tabs = listOf(
              getRuneTabUiState(timelineItem.drawnRune, Res.string.reading_tab_single_rune, timelineItem.topic),
              timelineItem.recipient?.let { getShareTabUiState(it, timelineItem) } ?: ReadingInterpretationTabUiState.Notes(Res.string.reading_tab_notes, timelineItem.notes)
            )
          )
        }
        is TimelineItem.PpfRuneReading -> {
          _uiState.value = ReadingInterpretationUiState(
            id = timelineItem.id,
            createdAt = timelineItem.createdAt.format(),
            topic = timelineItem.topic,
            recipient = timelineItem.recipient,
            question = timelineItem.question,
            tabs = listOf(
              getRuneTabUiState(timelineItem.pastRune, Res.string.reading_tab_past_rune, timelineItem.topic,Res.string.reading_position_past_description),
              getRuneTabUiState(timelineItem.presentRune, Res.string.reading_tab_present_rune, timelineItem.topic,Res.string.reading_position_present_description),
              getRuneTabUiState(timelineItem.futureRune,Res.string.reading_tab_future_rune, timelineItem.topic,Res.string.reading_position_future_description),
              timelineItem.recipient?.let { getShareTabUiState(it, timelineItem) } ?: ReadingInterpretationTabUiState.Notes(Res.string.reading_tab_notes, timelineItem.notes)
            )
          )
        }
        else -> {
          _uiEvent.emit(UiEvent.ShowError(Res.string.reading_load_error))
          return@launch
        }
      }
    }
  }

  private fun getReadingPdfDrawnRuneUiModel(
    drawnRune: DrawnRune,
    position: StringResource? = null,
    positionDescription: StringResource? = null,
    topic: ReadingTopic,
  ): ReadingPdfDrawnRuneUiModel =
    ReadingPdfDrawnRuneUiModel(
      rune = drawnRune.rune,
      orientation = drawnRune.orientation,
      position = position,
      positionDescription = positionDescription,
      interpretation = drawnRune.generalInterpretation(),
      supplementalInterpretation = drawnRune.supplementalInterpretation(topic),
      keywords = drawnRune.generalKeywords(),
      supplementalKeywords = drawnRune.supplementalKeywords(topic)
    )

  private fun getShareTabUiState(recipient: String, timelineItem: TimelineItem.SingleRuneReading): ReadingInterpretationTabUiState.Share =
    ReadingInterpretationTabUiState.Share(
      label = Res.string.reading_tab_share,
      recipient = recipient,
      notes = timelineItem.notes,
      pdfUiModel = ReadingPdfUiModel(
        id = timelineItem.id,
        createdAt = timelineItem.createdAt.formatAbsolute(),
        spread = RuneSpread.SINGLE_RUNE,
        topic = timelineItem.topic,
        recipient = recipient,
        question = timelineItem.question,
        personalMessage = timelineItem.notes,
        qrPayload = timelineItem.toReadingQrPayload(),
        runes = listOf(
          getReadingPdfDrawnRuneUiModel(drawnRune = timelineItem.drawnRune, topic = timelineItem.topic)
        )
      )
    )

  private fun getShareTabUiState(recipient: String, timelineItem: TimelineItem.PpfRuneReading): ReadingInterpretationTabUiState.Share =
    ReadingInterpretationTabUiState.Share(
      label = Res.string.reading_tab_share,
      recipient = recipient,
      notes = timelineItem.notes,
      pdfUiModel = ReadingPdfUiModel(
        id = timelineItem.id,
        createdAt = timelineItem.createdAt.formatAbsolute(),
        spread = RuneSpread.PAST_PRESENT_FUTURE,
        topic = timelineItem.topic,
        recipient = recipient,
        question = timelineItem.question,
        personalMessage = timelineItem.notes,
        qrPayload = timelineItem.toReadingQrPayload(),
        runes = listOf(
          getReadingPdfDrawnRuneUiModel(timelineItem.pastRune,Res.string.reading_tab_past_rune, Res.string.reading_position_past_description, timelineItem.topic),
          getReadingPdfDrawnRuneUiModel(timelineItem.presentRune, Res.string.reading_tab_present_rune, Res.string.reading_position_present_description, timelineItem.topic),
          getReadingPdfDrawnRuneUiModel(timelineItem.futureRune,Res.string.reading_tab_future_rune,Res.string.reading_position_future_description, timelineItem.topic)
        )
      )
    )

  private fun getRuneTabUiState(
    drawnRune: DrawnRune,
    tabName: StringResource,
    topic: ReadingTopic,
    tabDescription: StringResource? = null
  ): ReadingInterpretationTabUiState.Rune = ReadingInterpretationTabUiState.Rune(
    label = tabName,
    drawnRune = drawnRune,
    interpretation = drawnRune.generalInterpretation(),
    supplementalInterpretation = drawnRune.supplementalInterpretation(topic),
    keywords = drawnRune.generalKeywords(),
    supplementalKeywords = drawnRune.supplementalKeywords(topic),
    tabDescription = tabDescription
  )

  fun openDeleteDialog() {
    _uiState.update { it.copy(showDeleteDialog = true) }
  }

  fun dismissDeleteDialog() {
    _uiState.update { it.copy(showDeleteDialog = false) }
  }

  fun deleteReading() {
    viewModelScope.launch {
      val isDeleted = timelineRepository.deleteTimelineItem(_uiState.value.id)
      if (isDeleted) {
        _uiEvent.emit(UiEvent.NavigateBack)
      } else {
        _uiEvent.emit(UiEvent.ShowError(Res.string.timeline_delete_dialog_error))
      }
      dismissDeleteDialog() // close dialog regardless if delete succeeded
    }
  }

  fun saveNotes(notes: String) {
    viewModelScope.launch {
      val isSaved = timelineRepository.updateTimelineItem(
        id = id,
        notes = notes,
        title = null
      )
      if (isSaved) {
        _uiState.update {
          it.copy(
            tabs = it.tabs.map { tab ->
              if (tab is ReadingInterpretationTabUiState.Notes)
                tab.copy(notes = notes)
              else
                tab
            }
          )
        }
      } else {
        _uiEvent.emit(UiEvent.ShowError(Res.string.reading_notes_save_error))
      }
    }
  }

}