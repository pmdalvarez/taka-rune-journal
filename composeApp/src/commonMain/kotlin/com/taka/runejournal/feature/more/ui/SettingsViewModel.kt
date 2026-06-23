package com.taka.runejournal.feature.more.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.feature.more.domain.repository.SettingsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.settings_save_error
import taka_rune_journal.composeapp.generated.resources.settings_save_error_blank_name

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

  val uiState: StateFlow<SettingsUiState> =
    combine(
      repository.displayName,
      repository.reversedRunesEnabled,
    ) { displayName, reversedRunesEnabled ->
      SettingsUiState(
        displayName = displayName,
        reversedRunesEnabled = reversedRunesEnabled,
      )
    }.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = SettingsUiState(),
    )

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  fun setReversedRunesEnabled(enabled: Boolean) {
    viewModelScope.launch {
      try {
        repository.setReversedRunesEnabled(enabled)
      } catch (e: CancellationException) {
        throw e
      } catch (_: Exception) {
        _uiEvent.emit(UiEvent.ShowError(Res.string.settings_save_error))
      }
    }
  }

  fun setDisplayName(displayName: String) {
    viewModelScope.launch {
      try {
        if (displayName.isBlank()) {
          _uiEvent.emit(UiEvent.ShowError(Res.string.settings_save_error_blank_name))
        } else {
          repository.setDisplayName(displayName)
        }
      } catch (e: CancellationException) {
        throw e
      } catch (_: Exception) {
        _uiEvent.emit(UiEvent.ShowError(Res.string.settings_save_error))
      }
    }
  }
}