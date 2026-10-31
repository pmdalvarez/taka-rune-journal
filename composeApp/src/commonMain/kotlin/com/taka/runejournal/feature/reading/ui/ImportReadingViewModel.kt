package com.taka.runejournal.feature.reading.ui

import androidx.lifecycle.ViewModel
import com.taka.runejournal.core.ui.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ImportReadingViewModel : ViewModel() {

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

}
