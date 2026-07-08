package com.taka.runejournal.feature.reading.ui

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.core.domain.model.RuneSpread
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.feature.more.domain.repository.SettingsRepository
import com.taka.runejournal.feature.reading.domain.model.RuneVisualState
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.String
import kotlin.random.Random

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

  // To do randomize based on screen size
  fun randomizeRuneVisualStates(width: Float, height: Float) {
    _uiState.update {
      it.copy(
        runeVisualStates = Rune.entries.associateWith {
          RuneVisualState(
            position = Offset(
              x = Random.nextFloat() * 100f * width,
              y = Random.nextFloat() * 100f * height
            ),
            depth = Random.nextFloat() ,
            angle = Random.nextFloat() * 360
          )
        }
      )
    }
  }
}