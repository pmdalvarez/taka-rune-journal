package com.taka.runejournal.feature.reading.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.core.domain.model.RuneOrientation
import com.taka.runejournal.core.domain.model.RuneSpread
import com.taka.runejournal.core.ui.UiEvent
import com.taka.runejournal.feature.reading.data.decodeReadingQrPayload
import com.taka.runejournal.feature.reading.domain.model.ReadingQrPayload
import com.taka.runejournal.feature.timeline.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_import_error
import kotlin.time.Instant

class ImportReadingViewModel(
  private val timelineRepository: TimelineRepository
) : ViewModel() {

  private val _uiEvent = MutableSharedFlow<UiEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  fun importReading(qrCode: String) {
    viewModelScope.launch {
      // Decoding fails for QR codes that are not Taka readings
      val payload = runCatching { qrCode.decodeReadingQrPayload() }.getOrNull() as? ReadingQrPayload.V1
      val spread = payload?.let { RuneSpread.fromKey(it.spread) }
      val topic = payload?.let { ReadingTopic.fromKey(it.topic) }
      // Runes with unknown keys are dropped, which the rune count check below then catches
      val drawnRunes = payload?.drawnRunes?.mapNotNull { drawnRune ->
        val rune = Rune.fromKey(drawnRune.rune)
        val orientation = RuneOrientation.fromKey(drawnRune.orientation)
        if (rune != null && orientation != null) DrawnRune(rune, orientation) else null
      }

      if (payload == null || spread == null || topic == null || drawnRunes == null || drawnRunes.size != spread.runeCount) {
        _uiEvent.emit(UiEvent.ShowError(Res.string.reading_import_error))
        return@launch
      }

      val createdAt = Instant.fromEpochMilliseconds(payload.createdAt)
      val readingId: Long = when (spread) {
        RuneSpread.SINGLE_RUNE -> timelineRepository.createSingleRuneReading(
          recipient = null,
          question = payload.question,
          topic = topic,
          rune = drawnRunes[0],
          createdAt = createdAt,
        )
        RuneSpread.PAST_PRESENT_FUTURE -> timelineRepository.createPpfRuneReading(
          recipient = null,
          question = payload.question,
          topic = topic,
          pastRune = drawnRunes[0],
          presentRune = drawnRunes[1],
          futureRune = drawnRunes[2],
          createdAt = createdAt,
        )
      }
      _uiEvent.emit(UiEvent.NavigateToItem(itemId = readingId))
    }
  }

}
