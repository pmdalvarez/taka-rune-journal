package com.taka.runejournal.feature.reading.domain.model

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.RuneSpread
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface ReadingQrPayload {

  @Serializable
  @SerialName("reading_qr_payload_v1")
  data class V1(
    val spread: String,
    val createdAt: Long,
    val topic: String,
    val question: String?,
    val drawnRunes: List<ReadingQrDrawnRuneV1>,
  ) : ReadingQrPayload
}

@Serializable
data class ReadingQrDrawnRuneV1(
  val rune: String,
  val orientation: String,
)

fun DrawnRune.toReadingQrDrawnRuneV1() =
  ReadingQrDrawnRuneV1(
    rune = rune.key,
    orientation = orientation.key,
  )

fun TimelineItem.SingleRuneReading.toReadingQrPayloadV1(): ReadingQrPayload.V1 =
  ReadingQrPayload.V1(
    spread = RuneSpread.SINGLE_RUNE.key,
    createdAt = createdAt.toEpochMilliseconds(),
    topic = topic.key,
    question = question,
    drawnRunes = listOf(drawnRune.toReadingQrDrawnRuneV1()),
  )

fun TimelineItem.PpfRuneReading.toReadingQrPayloadV1(): ReadingQrPayload.V1 =
  ReadingQrPayload.V1(
    spread = RuneSpread.PAST_PRESENT_FUTURE.key,
    createdAt = createdAt.toEpochMilliseconds(),
    topic = topic.key,
    question = question,
    drawnRunes = listOf(
      pastRune.toReadingQrDrawnRuneV1(),
      presentRune.toReadingQrDrawnRuneV1(),
      futureRune.toReadingQrDrawnRuneV1()
    ),
  )