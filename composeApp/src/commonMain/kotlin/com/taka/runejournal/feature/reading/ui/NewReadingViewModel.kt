package com.taka.runejournal.feature.reading.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.RuneSpread
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.feature.more.domain.repository.SettingsRepository
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_draw_save_error

class NewReadingViewModel(
  private val settingsRepository: SettingsRepository,
  private val timelineRepository: TimelineRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow(NewReadingUiState())
  val uiState: StateFlow<NewReadingUiState> = _uiState.asStateFlow()

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  fun updateSelections(
    spread: RuneSpread,
    topic: ReadingTopic,
    question: String?,
  ) {
    _uiState.update {
      it.copy(
        spread = spread,
        question = question,
        topic = topic
      )
    }
    viewModelScope.launch {
      _uiEvent.emit(UiEvent.NavigateForward)
    }
  }

  fun saveAndNavigateToReading(drawnRunes: List<DrawnRune>) {
    val spread = _uiState.value.spread
    val question = _uiState.value.question
    val topic = _uiState.value.topic ?: ReadingTopic.GENERAL
println("XXXXXXXX saveAndNavigateToReading spread: $spread, question: $question, topic: $topic, drawnRunes: $drawnRunes")
    viewModelScope.launch {
      if (spread == null || drawnRunes.size < spread.runeCount) {
        _uiEvent.emit(UiEvent.ShowError(Res.string.reading_draw_save_error))
        return@launch
      }
      val readingId: Long = when (spread) {
        RuneSpread.SINGLE_RUNE-> timelineRepository.createSingleRuneReading(
          question = question,
          topic = topic,
          rune = drawnRunes[0],
        )
        RuneSpread.PAST_PRESENT_FUTURE -> timelineRepository.createPpfRuneReading(
          question = question,
          topic = topic,
          pastRune = drawnRunes[0],
          presentRune = drawnRunes[1],
          futureRune = drawnRunes[2],
        )
      }
      _uiEvent.emit(UiEvent.NavigateToItem(itemId = readingId))
    }
  }

}