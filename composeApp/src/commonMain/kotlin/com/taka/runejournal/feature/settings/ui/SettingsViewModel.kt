package com.taka.runejournal.feature.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taka.runejournal.feature.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = SettingsUiState(),
    )

  fun setReversedRunesEnabled(enabled: Boolean) {
    viewModelScope.launch {
      try {
        repository.setReversedRunesEnabled(enabled)
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        // TODO - show error snackbar
      }
    }
  }

  fun setDisplayName(displayName: String) {
    viewModelScope.launch {
      try {
        repository.setDisplayName(displayName)
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        // TODO - show error snackbar
      }
    }
  }
}