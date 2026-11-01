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
      // Decoding throws a SerializationException (an IllegalArgumentException) for QR codes that are not Taka readings
      val payload = try {
        qrCode.decodeReadingQrPayload()
      } catch (e: IllegalArgumentException) {
        null
      }

      val readingId = when (payload) {
        is ReadingQrPayload.V1 -> saveReading(payload)
        null -> null
      }

      if (readingId != null) {
        _uiEvent.emit(UiEvent.NavigateToItem(itemId = readingId))
      } else {
        _uiEvent.emit(UiEvent.ShowError(Res.string.reading_import_error))
      }
    }
  }

  /**
   * Saves a version 1 payload as a reading. Returns its id, or null if the payload contains unknown keys.
   */
  private suspend fun saveReading(payload: ReadingQrPayload.V1): Long? {
    val spread = RuneSpread.fromKey(payload.spread) ?: return null
    val topic = ReadingTopic.fromKey(payload.topic) ?: return null
    // Runes with unknown keys are dropped, which the rune count check below then catches
    val drawnRunes = payload.drawnRunes.mapNotNull { drawnRune ->
      val rune = Rune.fromKey(drawnRune.rune)
      val orientation = RuneOrientation.fromKey(drawnRune.orientation)
      if (rune != null && orientation != null) DrawnRune(rune, orientation) else null
    }
    if (drawnRunes.size != spread.runeCount) return null

    val createdAt = Instant.fromEpochMilliseconds(payload.createdAt)
    return when (spread) {
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
  }

}
